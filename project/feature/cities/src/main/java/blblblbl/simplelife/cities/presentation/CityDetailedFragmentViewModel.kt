package blblblbl.simplelife.cities.presentation

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blblblbl.simplelife.cities.domain.usecase.GetCitiesUseCase
import blblblbl.simplelife.cities.domain.usecase.GetForecastDBUseCase
import blblblbl.simplelife.cities.domain.usecase.SaveForecastUseCase
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.forecast.domain.usecase.GetForecastUseCase
import blblblbl.simplelife.settings.domain.usecase.GetAppConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CityDetailedFragmentViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val settingsUseCase: GetAppConfigUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    private val getForecastDBUseCase: GetForecastDBUseCase,
    private val saveForecastUseCase: SaveForecastUseCase
):ViewModel() {

    private val _forecast = MutableStateFlow<ForecastResponse?>(null)
    val forecast = _forecast.asStateFlow()

    private val _loadState = MutableStateFlow<LoadingState>(LoadingState.LOADED)
    val loadState = _loadState.asStateFlow()

    fun getDBForecast(name:String){
        viewModelScope.launch(Dispatchers.IO){
            _forecast.value = getForecastDBUseCase.getForecast(name)
        }
    }

    val settings = settingsUseCase.getAppConfigFlow()
    fun refresh(
        context: Context
    ){
        viewModelScope.launch {
            _loadState.value = LoadingState.LOADING
            try {
                _forecast.value?.location?.name?.let {name->
                    val forecast = getForecastUseCase.getForecastByName(name,7,"no","no")
                    _forecast.value = forecast
                    withContext(Dispatchers.IO){
                        saveForecastUseCase.saveForecast(forecast)
                    }
                }
            }
            catch (e:Throwable){
                Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
            }
            _loadState.value = LoadingState.LOADED
        }
    }
}