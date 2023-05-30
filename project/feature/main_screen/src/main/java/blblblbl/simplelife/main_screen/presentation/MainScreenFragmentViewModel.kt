package blblblbl.simplelife.main_screen.presentation

import android.content.Context
import android.location.LocationManager
import android.util.Log
import androidx.core.location.LocationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blblblbl.simplelife.autocomplete.domain.usecase.SearchCitiesVariantsUseCase
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.forecast.domain.model.location.Location
import blblblbl.simplelife.forecast.domain.usecase.GetForecastUseCase
import blblblbl.simplelife.main_screen.domain.usecase.DataBaseUseCase
import blblblbl.simplelife.main_screen.domain.usecase.LastSearchUseCase
import blblblbl.simplelife.main_screen.ui.UIError
import blblblbl.simplelife.settings.domain.usecase.GetAppConfigUseCase
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainScreenFragmentViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase,
    private val settingsUseCase: GetAppConfigUseCase,
    private val lastSearchUseCase: LastSearchUseCase,
    private val dataBaseUseCase: DataBaseUseCase,
    private val searchCitiesVariantsUseCase: SearchCitiesVariantsUseCase
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

    private val _errorText = MutableStateFlow<UIError?>(null)
    val errorText = _errorText.asStateFlow()

    private val _cityVariants = MutableStateFlow<List<String>?>(null)
    val cityVariants = _cityVariants.asStateFlow()
    private var autoCompleteJob: Job? = null

    var currentRequest :Job? = null

    init {
        getLast()
    }
    fun checkInFavourites(){
        viewModelScope.launch(Dispatchers.IO){
            _forecast.value?.location?.name?.let { name->
                _isInFavourites.value = dataBaseUseCase.isCityInFavourites(name)
            }
        }
    }
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    private fun getLast(){
        viewModelScope.launch {
            _forecast.value = lastSearchUseCase.getLast()
            withContext(Dispatchers.IO){
                _forecast.value?.location?.name?.let { name->
                    _isInFavourites.value = dataBaseUseCase.isCityInFavourites(name)
                }
            }
        }
    }
    fun addToFavourites(){
        viewModelScope.launch(Dispatchers.IO) {
            _forecast.value?.let { forecast ->
                dataBaseUseCase.saveForecast(forecast)
                _isInFavourites.value = true
            }
        }
    }
    fun removeFromFavourites(){
        _isInFavourites.value = false
        viewModelScope.launch(Dispatchers.IO) {
            _forecast.value?.location?.name?.let { name ->
                dataBaseUseCase.removeCity(name)
            }
        }
    }
    private suspend fun showError(message:String){
        _errorText.value = UIError(message)
    }

    private fun searchForecast(query: String, context: Context){
        currentRequest?.cancel()
        currentRequest = viewModelScope.launch(Dispatchers.IO) {
            val response = getForecastUseCase.getForecastByName(query,7,"yes","yes")
            response
                .suspendOnSuccess {
                    _forecast.value = this.data
                    withContext(Dispatchers.IO){
                        _forecast.value?.location?.name?.let { name->
                            _isInFavourites.value = dataBaseUseCase.isCityInFavourites(name)
                        }
                    }
                    if (_isInFavourites.value){
                        _forecast.value?.let { dataBaseUseCase.saveForecast(it) }
                    }
                }
                .onFailure {
                    viewModelScope.launch { showError(message()) }
                }
                .onError {
                    viewModelScope.launch { showError(statusCode.name) }
                }
                .onException {
                    viewModelScope.launch { showError("something's gone wrong, please check your internet connection") }
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
            val response = getForecastUseCase.getForecastByLoc(location,7,"yes","yes")
            response
                .suspendOnSuccess {
                    _forecast.value = this.data
                    withContext(Dispatchers.IO){
                        _forecast.value?.location?.name?.let { name->
                            _isInFavourites.value = dataBaseUseCase.isCityInFavourites(name)
                        }
                    }
                }
                .onFailure { viewModelScope.launch { showError(message()) } }
                .onError {
                    viewModelScope.launch { showError(statusCode.name) }
                }
                .onException {
                    viewModelScope.launch { showError("something's gone wrong, please check your internet connection") }
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
                viewModelScope.launch { showError("enable gps") }
                _loadState.value = LoadingState.LOADED
            }
            else{
                try {
                    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                    val cancellation = CancellationTokenSource()
                    val locTask = fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY ,cancellation.token)
                    locTask.addOnSuccessListener {location->
                        location?.let { location ->
                            getForecastByLocation(Location(longitude = location.longitude, latitude = location.latitude))
                        }
                    }
                }
                catch (e:Throwable){
                    e.message?.let { viewModelScope.launch { showError(it) } }
                }
            }
        }
    }
    fun refresh(context:Context){
        _loadState.value = LoadingState.LOADING
        _forecast.value?.location?.name?.let { searchForecast(it,context) }
    }

    fun searchCompletions(){
        autoCompleteJob?.cancel()
        autoCompleteJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                _cityVariants.value = searchCitiesVariantsUseCase.execute(_searchQuery.value)
            }
            catch (e:Throwable){ }
        }
    }
    fun clearCompletions(){
        _cityVariants.value = listOf<String>()
    }
}