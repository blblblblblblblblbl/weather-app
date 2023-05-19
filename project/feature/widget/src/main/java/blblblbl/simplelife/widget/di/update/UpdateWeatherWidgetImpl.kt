package blblblbl.simplelife.widget.di.update

import android.content.Context
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidgetManager
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig
import blblblbl.simplelife.widget.mapForecastToWidget
import blblblbl.simplelife.widget.mapWeatherConfigToWidget
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UpdateWeatherWidgetImpl @Inject constructor(
    @ApplicationContext private val context: Context
):UpdateWeatherWidget {
    override suspend fun updateForecast(context:Context, forecastResponse: ForecastResponse) {
        Log.d("MyLog","UpdateWeatherWidgetImpl")
        GlanceAppWidgetManager(context).mapForecastToWidget(context, forecastResponse)
    }


    override suspend fun updateWeatherConfig(weatherConfig: WeatherConfig) =
        GlanceAppWidgetManager(context).mapWeatherConfigToWidget(context, weatherConfig)
}