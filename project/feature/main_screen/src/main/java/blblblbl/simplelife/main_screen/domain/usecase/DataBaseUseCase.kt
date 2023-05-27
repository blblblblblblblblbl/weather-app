package blblblbl.simplelife.main_screen.domain.usecase

import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.main_screen.domain.repository.DataBaseRepository
import javax.inject.Inject

class DataBaseUseCase @Inject constructor(
    private val dataBaseRepository: DataBaseRepository
) {
    suspend fun saveForecast(forecastResponse: ForecastResponse) =
        dataBaseRepository.saveForecast(forecastResponse)

    suspend fun removeCity(name: String) =
        dataBaseRepository.removeCity(name)

    suspend fun isCityInFavourites(name:String): Boolean =
        dataBaseRepository.isCityInFavourites(name)
}