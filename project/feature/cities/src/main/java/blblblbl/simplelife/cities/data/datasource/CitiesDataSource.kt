package blblblbl.simplelife.cities.data.datasource

import androidx.paging.PagingData
import blblblbl.simplelife.cities.data.model.ForecastResponse
import kotlinx.coroutines.flow.Flow

interface CitiesDataSource {
    fun getCitiesPagingDataFlow(pageSize:Int): Flow<PagingData<ForecastResponse>>
}