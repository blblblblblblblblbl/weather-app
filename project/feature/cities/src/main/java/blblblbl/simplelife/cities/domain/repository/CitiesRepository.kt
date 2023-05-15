package blblblbl.simplelife.cities.domain.repository

import androidx.paging.PagingData
import blblblbl.simplelife.cities.domain.model.ForecastResponse
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    fun getCitiesPagingDataFlow(pageSize:Int): Flow<PagingData<ForecastResponse>>

    suspend fun removeCity(name: String)
}