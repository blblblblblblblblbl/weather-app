package blblblbl.simplelife.main_screen.presentation

import android.content.Context
import android.location.LocationManager
import android.widget.Toast
import androidx.core.location.LocationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.forecast.domain.model.location.Location
import blblblbl.simplelife.forecast.domain.usecase.GetForecastUseCase
import blblblbl.simplelife.main_screen.domain.usecase.DataBaseUseCase
import blblblbl.simplelife.main_screen.domain.usecase.LastSearchUseCase
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig
import blblblbl.simplelife.settings.domain.usecase.GetAppConfigUseCase
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainScreenFragmentViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase,
    private val settingsUseCase: GetAppConfigUseCase,
    private val lastSearchUseCase: LastSearchUseCase,
    private val dataBaseUseCase: DataBaseUseCase
):ViewModel() {

    private val _forecast = MutableStateFlow<ForecastResponse?>(null)
    val forecast = _forecast.asStateFlow()

    private val _searchQuery = MutableStateFlow<String>("")
    val searchQuery = _searchQuery.asStateFlow()

    val settings = settingsUseCase.getAppConfigFlow()

    private val _loadState = MutableStateFlow<LoadingState>(LoadingState.LOADED)
    val loadState = _loadState.asStateFlow()

    private val _isInFavourites = MutableStateFlow<Boolean>(true)
    val isInFavourites = _isInFavourites.asStateFlow()

    var currentRequest :Job? = null

    init {
        getLast()
    }
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    fun getLast(){
        viewModelScope.launch {
            _forecast.value = lastSearchUseCase.getLast()
            withContext(Dispatchers.IO){
                _forecast.value?.location?.name?.let { name->
                    _isInFavourites.value = dataBaseUseCase.isCityInFavourites(name)
                }
            }
        }
    }
    fun saveForecastToFavourites(){
        viewModelScope.launch(Dispatchers.IO) {
            _forecast.value?.let { forecast ->
                dataBaseUseCase.saveForecast(forecast)
                _isInFavourites.value = true
            }
        }
    }

    private fun searchForecast(query: String, context: Context){
        currentRequest?.cancel()
        currentRequest = viewModelScope.launch {
            try {
                val forecast = getForecastUseCase.getForecastByName(query,7,"no","no")
                _forecast.value = forecast
                _isInFavourites.value = false
                lastSearchUseCase.saveLast(forecast)
            }
            catch (e:Throwable){
                Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
            }
            withContext(Dispatchers.IO){
                _forecast.value?.location?.name?.let { name->
                    _isInFavourites.value = dataBaseUseCase.isCityInFavourites(name)
                }
            }
            if (_isInFavourites.value){
                _forecast.value?.let { dataBaseUseCase.saveForecast(it) }
            }
            currentRequest = null
            _loadState.value = LoadingState.LOADED
        }
    }
    fun getForecastByName(context:Context){
        _loadState.value = LoadingState.LOADING
        searchForecast(_searchQuery.value,context)
    }
    private fun getForecastByLocation(location:Location){
        currentRequest?.cancel()
        currentRequest = viewModelScope.launch {
            val job = viewModelScope.launch {
                val forecast = getForecastUseCase.getForecastByLoc(location,7,"no","no")
                _forecast.value = forecast
                _isInFavourites.value = false
                lastSearchUseCase.saveLast(forecast)
            }
            job.join()
            withContext(Dispatchers.IO){
                _forecast.value?.location?.name?.let { name->
                    _isInFavourites.value = dataBaseUseCase.isCityInFavourites(name)
                }
            }
            if (_isInFavourites.value){
                _forecast.value?.let { dataBaseUseCase.saveForecast(it) }
            }
            currentRequest = null
            _loadState.value = LoadingState.LOADED
        }

    }
    fun locationOnClick(context:Context){
        viewModelScope.launch {
            _loadState.value = LoadingState.LOADING
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isLocEnabled = LocationManagerCompat.isLocationEnabled(locationManager)
            if (!isLocEnabled) {
                Toast.makeText(context,"enable gps",Toast.LENGTH_SHORT).show()
                _loadState.value = LoadingState.LOADED
            }
            else{
                try {
                    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                    val cancellation = CancellationTokenSource()
                    val locTask = fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY ,cancellation.token)
                    locTask.addOnSuccessListener {location->
                        getForecastByLocation(Location(longitude = location.longitude, latitude = location.latitude))
                    }
                }
                catch (e:Throwable){
                    Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun refresh(context:Context){
        _loadState.value = LoadingState.LOADING
        _forecast.value?.location?.name?.let { searchForecast(it,context) }
    }
}