package blblblbl.simplelife.weather.di.database.cities

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import blblblbl.simplelife.cities.data.database.CitiesDatabase
import blblblbl.simplelife.database.model.CityWeatherEntity
import blblblbl.simplelife.forecast.data.model.forecast.ForecastResponse
import blblblbl.simplelife.forecast.data.utils.mapToDomain
import blblblbl.simplelife.weather.di.database.DataBaseCreator
import blblblbl.simplelife.widget.di.update.UpdateWeatherWidget
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


@Module
@InstallIn(SingletonComponent::class)
class CitiesDBModule {

    @Provides
    fun provideCityDB(dbCreator:DataBaseCreator, updateWeatherWidget: UpdateWeatherWidget):CitiesDatabase =
        object :CitiesDatabase{
            override fun getCitiesPagingDataFlow(pageSize: Int): Flow<PagingData<ForecastResponse>> =
                Pager(
                    config = PagingConfig(pageSize = pageSize),
                    pagingSourceFactory = {dbCreator.getDB().cityDao().getCitiesForecastPagingSource()}
                ).flow.map {pagingData->
                    pagingData.map { it.forecast.mapToCities()}
                }

            override suspend fun removeCity(name: String) =
                dbCreator.getDB().cityDao().deleteCity(name)

            override suspend fun saveForecast(forecastResponse: ForecastResponse) {
                forecastResponse.location?.name?.let { name->
                    val entity = CityWeatherEntity(
                        name = name,
                        forecast = forecastResponse.mapToDB()
                    )
                    dbCreator.getDB().cityDao().update(entity)
                    updateWeatherWidget.updateForecast(forecastResponse.mapToDomain())
                }
            }

            override suspend fun getForecast(name: String): ForecastResponse? =
                dbCreator.getDB().cityDao().getCityForecast(name)?.mapToCities()
        }
}