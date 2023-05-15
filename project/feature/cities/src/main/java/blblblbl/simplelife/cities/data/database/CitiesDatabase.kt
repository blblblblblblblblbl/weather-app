package blblblbl.simplelife.cities.data.database

import androidx.paging.PagingData
import blblblbl.simplelife.cities.data.model.ForecastResponse
import kotlinx.coroutines.flow.Flow

interface CitiesDatabase {

    fun getCitiesPagingDataFlow(pageSize:Int): Flow<PagingData<ForecastResponse>>

    suspend fun removeCity(name: String)
}