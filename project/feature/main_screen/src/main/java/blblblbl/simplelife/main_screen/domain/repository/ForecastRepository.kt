package blblblbl.simplelife.main_screen.domain.repository

import blblblbl.simplelife.forecast.domain.model.ForecastResponse

interface ForecastRepository {
    suspend fun getLast(): ForecastResponse?

    suspend fun saveLast(forecast: ForecastResponse)
}