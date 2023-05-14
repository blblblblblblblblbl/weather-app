package blblblbl.simplelife.main_screen.data.database

import blblblbl.simplelife.forecast.data.model.forecast.ForecastResponse

interface CityDataBase {

    suspend fun saveForecast(forecastResponse: ForecastResponse)

    suspend fun isCityInFavourites(name:String): Boolean

}