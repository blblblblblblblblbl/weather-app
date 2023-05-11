package blblblbl.simplelife.main_screen.presentation

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.forecast.domain.model.location.Location
import blblblbl.simplelife.forecast.domain.usecase.GetForecastUseCase
import blblblbl.simplelife.main_screen.domain.usecase.LastSearchUseCase
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenFragmentViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase,
    private val lastSearchUseCase: LastSearchUseCase
):ViewModel() {

    private val _forecast = MutableStateFlow<ForecastResponse?>(null)
    val forecast = _forecast.asStateFlow()

    private val _searchQuery = MutableStateFlow<String>("")
    val searchQuery = _searchQuery.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    fun getLast(){
        viewModelScope.launch {
            _forecast.value = lastSearchUseCase.getLast()
        }
    }
    private fun searchForecast(query: String, context: Context){
        viewModelScope.launch {
            try {
                val forecast = getForecastUseCase.getForecastByName(query,7,"no","no")
                _forecast.value = forecast
                lastSearchUseCase.saveLast(forecast)
            }
            catch (e:Throwable){
                Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun getForecastByName(context:Context){
        searchForecast(_searchQuery.value,context)
    }
    fun getForecastByLocationa(location:Location){
        viewModelScope.launch {
            val forecast = getForecastUseCase.getForecastByLoc(location,7,"no","no")
            _forecast.value = forecast
            lastSearchUseCase.saveLast(forecast)
        }
    }
    fun getForecastByLocation(context:Context){
        viewModelScope.launch {
            try {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                val cancellation = CancellationTokenSource()
                val locRequest = CurrentLocationRequest.Builder().build()
                val locTask = fusedLocationClient.getCurrentLocation(locRequest,cancellation.token)
                locTask.addOnSuccessListener {location->
                    getForecastByLocationa(Location(longitude = location.longitude, latitude = location.latitude))
                }
            }
            catch (e:Throwable){
                Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun refresh(context:Context){
        _forecast.value?.location?.name?.let { searchForecast(it,context) }
    }
}