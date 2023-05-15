package blblblbl.simplelife.cities.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import blblblbl.simplelife.cities.domain.model.ForecastResponse
import blblblbl.simplelife.cities.domain.usecase.GetCitiesUseCase
import blblblbl.simplelife.settings.domain.usecase.GetAppConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesFragmentViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val settingsUseCase: GetAppConfigUseCase,
) : ViewModel() {

    val pagedCities: Flow<PagingData<ForecastResponse>> =
        getCitiesUseCase.execute(PAGE_SIZE).cachedIn(viewModelScope)

    val settings = settingsUseCase.getAppConfigFlow()
    fun removeCity(name: String){
        viewModelScope.launch(Dispatchers.IO) {
            getCitiesUseCase.removeCity(name)
        }
    }

    companion object {
        const val PAGE_SIZE: Int = 10
    }
}