package blblblbl.simplelife.forecast.data.datasource

import blblblbl.simplelife.forecast.data.model.ForecastResponse
import blblblbl.simplelife.forecast.data.network.ForecastApi
import javax.inject.Inject

class ForecastDataSourceImpl @Inject constructor(
    private val forecastApi: ForecastApi
):ForecastDataSource {
    override suspend fun getForecast(
        query: String,
        days: Int,
        aqi: String,
        alerts: String
    ): ForecastResponse =
        forecastApi.getForecast(query,days,aqi, alerts)

    override suspend fun getCurrent(query: String, aqi: String, lang: String): ForecastResponse =
        forecastApi.getCurrent(query,aqi, lang)
}