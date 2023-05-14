package blblblbl.simplelife.cities.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import blblblbl.simplelife.cities.domain.model.ForecastResponse
import blblblbl.simplelife.cities.domain.usecase.GetCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CitiesFragmentViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase
) : ViewModel() {

    val pagedCities: Flow<PagingData<ForecastResponse>> =
        getCitiesUseCase.execute(PAGE_SIZE).cachedIn(viewModelScope)

    companion object {
        const val PAGE_SIZE: Int = 10
    }
}