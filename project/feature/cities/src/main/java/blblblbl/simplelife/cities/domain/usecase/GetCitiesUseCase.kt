package blblblbl.simplelife.cities.domain.usecase

import androidx.paging.PagingData
import blblblbl.simplelife.cities.domain.repository.CitiesRepository
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val repository: CitiesRepository
) {
    fun execute(pageSize:Int): Flow<PagingData<ForecastResponse>> =
        repository.getCitiesPagingDataFlow(pageSize)

    suspend fun removeCity(name: String) =
        repository.removeCity(name)
}