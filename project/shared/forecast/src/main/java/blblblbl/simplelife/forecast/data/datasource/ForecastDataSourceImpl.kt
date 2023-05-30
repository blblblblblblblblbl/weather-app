package blblblbl.simplelife.forecast.data.datasource

import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.forecast.domain.model.location.Location
import blblblbl.simplelife.forecast.data.network.ForecastApi
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class ForecastDataSourceImpl @Inject constructor(
    private val forecastApi: ForecastApi
):ForecastDataSource {
    override suspend fun getForecast(
        query: String,
        days: Int,
        aqi: String,
        alerts: String
    ): ApiResponse<ForecastResponse> =
        forecastApi.getForecast(query,days,aqi, alerts)

    override suspend fun getForecastByLoc(
        loc: Location,
        days: Int,
        aqi: String,
        alerts: String
    ): ApiResponse<ForecastResponse> {
        val query = "${loc.latitude},${loc.longitude}"
        return forecastApi.getForecast(query,days,aqi, alerts)
    }


    override suspend fun getCurrent(query: String, aqi: String, lang: String): ApiResponse<ForecastResponse> =
        forecastApi.getCurrent(query,aqi, lang)
}