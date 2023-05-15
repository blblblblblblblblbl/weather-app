package blblblbl.simplelife.cities.domain.usecase

import blblblbl.simplelife.cities.domain.repository.CitiesRepository
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import javax.inject.Inject

class SaveForecastUseCase @Inject constructor(
    private val repository: CitiesRepository
) {
    suspend fun saveForecast(forecastResponse: ForecastResponse) =
        repository.saveForecast(forecastResponse)
}