package blblblbl.simplelife.forecast.data.datasource

import blblblbl.simplelife.forecast.data.model.forecast.ForecastResponse
import blblblbl.simplelife.forecast.data.model.location.Location

interface ForecastDataSource {

    suspend fun getForecast(
        query: String,
        days: Int,
        aqi: String,
        alerts: String
    ): ForecastResponse

    suspend fun getForecastByLoc(
        loc: Location,
        days: Int,
        aqi: String,
        alerts: String
    ): ForecastResponse

    suspend fun getCurrent(
        query: String,
        aqi: String,
        lang: String = "DEFAULT_LANGUAGE"
    ): ForecastResponse
}