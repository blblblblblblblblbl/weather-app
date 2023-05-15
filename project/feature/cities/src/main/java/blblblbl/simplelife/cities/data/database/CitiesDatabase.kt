package blblblbl.simplelife.cities.data.database

import androidx.paging.PagingData
import blblblbl.simplelife.forecast.data.model.forecast.ForecastResponse
import kotlinx.coroutines.flow.Flow

interface CitiesDatabase {

    fun getCitiesPagingDataFlow(pageSize:Int): Flow<PagingData<ForecastResponse>>

    suspend fun removeCity(name: String)

    suspend fun saveForecast(forecastResponse: ForecastResponse)

    suspend fun getForecast(name: String):ForecastResponse?
}