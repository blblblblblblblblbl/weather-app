package blblblbl.simplelife.forecast.domain.repository

import blblblbl.simplelife.forecast.domain.model.location.Location
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import com.skydoves.sandwich.ApiResponse


interface ForecastRepository {
    suspend fun getForecast(
        query: String,
        days: Int,
        aqi: String,
        alerts: String
    ): ApiResponse<ForecastResponse>

    suspend fun getForecastByLoc(
        loc: Location,
        days: Int,
        aqi: String,
        alerts: String
    ): ApiResponse<ForecastResponse>
    suspend fun getCurrent(
        query: String,
        aqi: String
    ): ApiResponse<ForecastResponse>
}