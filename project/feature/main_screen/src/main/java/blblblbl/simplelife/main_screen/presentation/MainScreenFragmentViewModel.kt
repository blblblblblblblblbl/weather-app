package blblblbl.simplelife.main_screen.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blblblbl.simplelife.forecast.domain.model.ForecastResponse
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
    fun getForecast(context:Context){
        viewModelScope.launch {
            try {
                val forecast = getForecastUseCase.execute(_searchQuery.value,7,"no","no")
                _forecast.value = forecast
                lastSearchUseCase.saveLast(forecast)
            }
            catch (e:Throwable){
                Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}