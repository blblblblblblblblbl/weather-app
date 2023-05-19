package blblblbl.simplelife.widget.di.update

import android.content.Context
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig

interface UpdateWeatherWidget {
    suspend fun updateForecast(context: Context, forecastResponse: ForecastResponse)

    suspend fun updateWeatherConfig(weatherConfig: WeatherConfig)
}