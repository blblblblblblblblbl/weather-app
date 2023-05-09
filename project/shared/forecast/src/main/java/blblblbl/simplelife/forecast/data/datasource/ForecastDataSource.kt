package blblblbl.simplelife.forecast.data.datasource

import blblblbl.simplelife.forecast.data.model.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastDataSource {

    suspend fun getForecast(
        query: String,
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