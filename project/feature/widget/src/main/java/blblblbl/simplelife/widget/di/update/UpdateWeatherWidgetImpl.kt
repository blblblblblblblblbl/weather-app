package blblblbl.simplelife.widget.di.update

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.widget.mapForecastToWidget
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UpdateWeatherWidgetImpl @Inject constructor(
    @ApplicationContext private val context: Context
):UpdateWeatherWidget {
    override suspend fun update(forecastResponse: ForecastResponse) =
        GlanceAppWidgetManager(context).mapForecastToWidget(context, forecastResponse)
}