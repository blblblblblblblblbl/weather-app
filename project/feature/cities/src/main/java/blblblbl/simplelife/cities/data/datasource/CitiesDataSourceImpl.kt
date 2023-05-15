package blblblbl.simplelife.cities.data.datasource

import androidx.paging.PagingData
import blblblbl.simplelife.cities.data.database.CitiesDatabase
import blblblbl.simplelife.forecast.data.model.forecast.ForecastResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CitiesDataSourceImpl @Inject constructor(
    private val citiesDatabase: CitiesDatabase
):CitiesDataSource {
    override fun getCitiesPagingDataFlow(pageSize: Int): Flow<PagingData<ForecastResponse>> =
        citiesDatabase.getCitiesPagingDataFlow(pageSize)

    override suspend fun removeCity(name: String) =
        citiesDatabase.removeCity(name)

    override suspend fun saveForecast(forecastResponse: ForecastResponse) =
        citiesDatabase.saveForecast(forecastResponse)
}