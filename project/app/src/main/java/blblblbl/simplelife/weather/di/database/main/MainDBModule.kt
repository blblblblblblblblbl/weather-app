package blblblbl.simplelife.weather.di.database.main

import blblblbl.simplelife.database.model.CityWeatherEntity
import blblblbl.simplelife.forecast.data.model.forecast.ForecastResponse
import blblblbl.simplelife.main_screen.data.database.CityDataBase
import blblblbl.simplelife.weather.di.database.DataBaseCreator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class MainDBModule{

    @Provides
    fun provideSearchDB(dbCreator: DataBaseCreator): CityDataBase =
        object :CityDataBase{
            override suspend fun saveForecast(forecastResponse: ForecastResponse) {
                forecastResponse.location?.name?.let { name->
                    val entity = CityWeatherEntity(
                        name = name,
                        forecast = forecastResponse.mapToDB()
                    )
                    dbCreator.getDB().cityDao().insert(entity)
                }
            }

            override suspend fun isCityInFavourites(name:String): Boolean =
                (dbCreator.getDB().cityDao().getCityForecast(name) != null)


        }
}