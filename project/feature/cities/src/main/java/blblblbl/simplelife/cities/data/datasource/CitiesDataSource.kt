package blblblbl.simplelife.cities.data.datasource

import androidx.paging.PagingData
import blblblbl.simplelife.forecast.data.model.forecast.ForecastResponse

import kotlinx.coroutines.flow.Flow

interface CitiesDataSource {
    fun getCitiesPagingDataFlow(pageSize:Int): Flow<PagingData<ForecastResponse>>

    suspend fun removeCity(name: String)

    suspend fun saveForecast(forecastResponse: ForecastResponse)
}