package blblblbl.simplelife.forecast.data.datasource

import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.forecast.domain.model.location.Location
import com.skydoves.sandwich.ApiResponse

interface ForecastDataSource {

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
        aqi: String,
        lang: String = "DEFAULT_LANGUAGE"
    ): ApiResponse<ForecastResponse>
}