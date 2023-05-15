package blblblbl.simplelife.cities.presentation

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import blblblbl.simplelife.cities.domain.usecase.GetCitiesUseCase
import blblblbl.simplelife.cities.domain.usecase.SaveForecastUseCase
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.forecast.domain.usecase.GetForecastUseCase
import blblblbl.simplelife.settings.domain.usecase.GetAppConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CitiesFragmentViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val settingsUseCase: GetAppConfigUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    private val saveForecastUseCase: SaveForecastUseCase
) : ViewModel() {

    val pagedCities: Flow<PagingData<ForecastResponse>> =
        getCitiesUseCase.execute(PAGE_SIZE).cachedIn(viewModelScope)

    val settings = settingsUseCase.getAppConfigFlow()
    fun removeCity(name: String){
        viewModelScope.launch(Dispatchers.IO) {
            getCitiesUseCase.removeCity(name)
        }
    }
    fun refreshCity(
        context:Context,
        name:String,
        loadStateChangeFun:(LoadingState)->Unit
    ){
        viewModelScope.launch {
            loadStateChangeFun(LoadingState.LOADING)
            try {
                val forecast = getForecastUseCase.getForecastByName(name,7,"no","no")
                withContext(Dispatchers.IO){
                    saveForecastUseCase.saveForecast(forecast)
                }
            }
            catch (e:Throwable){
                Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
            }
            loadStateChangeFun(LoadingState.LOADED)
        }
    }
    companion object {
        const val PAGE_SIZE: Int = 10
    }
}