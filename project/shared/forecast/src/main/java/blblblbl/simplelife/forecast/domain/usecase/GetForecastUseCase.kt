package blblblbl.simplelife.forecast.domain.usecase


import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.forecast.domain.model.location.Location
import blblblbl.simplelife.forecast.domain.repository.ForecastRepository
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val forecastRepository: ForecastRepository
) {
    suspend fun getForecastByName(
        query: String,
        days: Int,
        aqi: String,
        alerts: String
    ): ApiResponse<ForecastResponse> =
        forecastRepository.getForecast(query, days, aqi, alerts)
    suspend fun getForecastByLoc(
        loc: Location,
        days: Int,
        aqi: String,
        alerts: String
    ): ApiResponse<ForecastResponse> =
        forecastRepository.getForecastByLoc(loc, days, aqi, alerts)
}