package blblblbl.simplelife.cities.domain.repository

import androidx.paging.PagingData
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    fun getCitiesPagingDataFlow(pageSize:Int): Flow<PagingData<ForecastResponse>>

    suspend fun removeCity(name: String)

    suspend fun saveForecast(forecastResponse: ForecastResponse)

    suspend fun getForecast(name: String): ForecastResponse?
}