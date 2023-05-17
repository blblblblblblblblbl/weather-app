package blblblbl.simplelife.widget.di.update

import android.content.Context
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse

interface UpdateWeatherWidget {
    suspend fun update(forecastResponse: ForecastResponse)
}