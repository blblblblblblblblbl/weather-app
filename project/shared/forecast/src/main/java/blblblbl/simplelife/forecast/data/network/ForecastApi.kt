package blblblbl.simplelife.forecast.data.network

import blblblbl.simplelife.forecast.data.model.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {
    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") q: String,
        @Query("days") days: Int,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String
    ): ForecastResponse

    @GET("current.json")
    suspend fun getCurrent(
        @Query("q") q: String,
        @Query("aqi") aqi: String,
        @Query("lang") lang: String = "DEFAULT_LANGUAGE"
    ): ForecastResponse
}