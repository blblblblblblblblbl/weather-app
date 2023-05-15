package blblblbl.simplelife.main_screen.data.repository


import blblblbl.simplelife.forecast.data.utils.mapToData
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.main_screen.data.database.CityDataBase
import blblblbl.simplelife.main_screen.domain.repository.DataBaseRepository
import javax.inject.Inject

class DataBaseRepositoryImpl @Inject constructor(
    private val cityDataBase: CityDataBase
):DataBaseRepository {
    override suspend fun saveForecast(forecastResponse: ForecastResponse) =
        cityDataBase.saveForecast(forecastResponse.mapToData())

    override suspend fun isCityInFavourites(name: String): Boolean =
        cityDataBase.isCityInFavourites(name)
}