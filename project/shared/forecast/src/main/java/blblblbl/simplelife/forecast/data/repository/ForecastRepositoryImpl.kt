package blblblbl.simplelife.forecast.data.repository

import blblblbl.simplelife.forecast.data.datasource.ForecastDataSource
import blblblbl.simplelife.forecast.data.utils.mapToDomain
import blblblbl.simplelife.forecast.domain.model.ForecastResponse
import blblblbl.simplelife.forecast.domain.repository.ForecastRepository
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val forecastDataSource: ForecastDataSource
):ForecastRepository {
    override suspend fun getForecast(
        query: String,
        days: Int,
        aqi: String,
        alerts: String
    ): ForecastResponse =
        forecastDataSource.getForecast(query, days, aqi, alerts).mapToDomain()

    override suspend fun getCurrent(query: String, aqi: String): ForecastResponse =
        forecastDataSource.getCurrent(query, aqi).mapToDomain()
}