package blblblbl.simplelife.forecast.domain.repository

import blblblbl.simplelife.forecast.domain.model.ForecastResponse


interface ForecastRepository {
    suspend fun getForecast(
        query: String,
        days: Int,
        aqi: String,
        alerts: String
    ): ForecastResponse

    suspend fun getCurrent(
        query: String,
        aqi: String
    ): ForecastResponse
}