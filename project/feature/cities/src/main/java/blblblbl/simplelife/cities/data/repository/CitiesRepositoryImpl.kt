package blblblbl.simplelife.cities.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import blblblbl.simplelife.cities.data.datasource.CitiesDataSource
import blblblbl.simplelife.cities.domain.repository.CitiesRepository
import blblblbl.simplelife.forecast.data.utils.mapToData
import blblblbl.simplelife.forecast.data.utils.mapToDomain
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CitiesRepositoryImpl @Inject constructor(
    private val citiesDataSource: CitiesDataSource
): CitiesRepository {
    override fun getCitiesPagingDataFlow(pageSize: Int): Flow<PagingData<ForecastResponse>> =
        citiesDataSource.getCitiesPagingDataFlow(pageSize).map { pagingData->
            pagingData.map { forecastResponse->
                forecastResponse.mapToDomain()
            }
        }

    override suspend fun removeCity(name: String) =
        citiesDataSource.removeCity(name)

    override suspend fun saveForecast(forecastResponse: ForecastResponse) =
        citiesDataSource.saveForecast(forecastResponse.mapToData())
}