package blblblbl.simplelife.main_screen.domain.usecase

import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.main_screen.di.LastSearchFeature
import blblblbl.simplelife.main_screen.domain.repository.ForecastRepository
import javax.inject.Inject

class LastSearchUseCase @Inject constructor(
    @LastSearchFeature val repository: ForecastRepository
) {
    suspend fun getLast(): ForecastResponse? =
        repository.getLast()

    suspend fun saveLast(forecast: ForecastResponse) =
        repository.saveLast(forecast)
}