package blblblbl.simplelife.forecast.domain.usecase

import blblblbl.simplelife.forecast.domain.repository.ForecastRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val forecastRepository: ForecastRepository
){
    suspend fun execute(
        query: String,
        days: Int,
        aqi: String,
        alerts: String
    ) =
        forecastRepository.getForecast(query, days, aqi, alerts)
}