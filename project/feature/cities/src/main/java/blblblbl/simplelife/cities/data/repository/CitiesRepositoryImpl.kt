package blblblbl.simplelife.cities.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import blblblbl.simplelife.cities.data.datasource.CitiesDataSource
import blblblbl.simplelife.cities.data.utils.mapToDomain
import blblblbl.simplelife.cities.domain.model.ForecastResponse
import blblblbl.simplelife.cities.domain.repository.CitiesRepository
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
}