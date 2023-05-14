package blblblbl.simplelife.main_screen.domain.repository

import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse

interface DataBaseRepository {
    suspend fun saveForecast(forecastResponse: ForecastResponse)

    suspend fun isCityInFavourites(name:String): Boolean
}