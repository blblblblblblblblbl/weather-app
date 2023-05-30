package blblblbl.simplelife.forecast.data.repository

import blblblbl.simplelife.forecast.data.datasource.ForecastDataSource
import blblblbl.simplelife.forecast.data.utils.mapToData
import blblblbl.simplelife.forecast.domain.model.location.Location
import blblblbl.simplelife.forecast.data.utils.mapToDomain
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.forecast.domain.repository.ForecastRepository
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val forecastDataSource: ForecastDataSource
):ForecastRepository {
    override suspend fun getForecast(
        query: String,
        days: Int,
        aqi: String,
        alerts: String
    ): ApiResponse<ForecastResponse> =
        forecastDataSource.getForecast(query, days, aqi, alerts)

    override suspend fun getForecastByLoc(
        loc: Location,
        days: Int,
        aqi: String,
        alerts: String
    ): ApiResponse<ForecastResponse> =
        forecastDataSource.getForecastByLoc(loc, days, aqi, alerts)

    override suspend fun getCurrent(query: String, aqi: String): ApiResponse<ForecastResponse> =
        forecastDataSource.getCurrent(query, aqi)
}
