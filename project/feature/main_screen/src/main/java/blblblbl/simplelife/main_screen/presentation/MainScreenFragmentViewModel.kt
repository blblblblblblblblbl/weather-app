package blblblbl.simplelife.main_screen.presentation

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.forecast.domain.model.location.Location
import blblblbl.simplelife.forecast.domain.usecase.GetForecastUseCase
import blblblbl.simplelife.main_screen.domain.usecase.LastSearchUseCase
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
    fun getForecastByLocation(context:Context){
        val loc = Location(longitude = 54.9833, latitude = 82.8964)
        viewModelScope.launch {
            try {
                val forecast = getForecastUseCase.getForecastByLoc(loc,7,"no","no")
                _forecast.value = forecast
                lastSearchUseCase.saveLast(forecast)
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