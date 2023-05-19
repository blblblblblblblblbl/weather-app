package blblblbl.simplelife.widget

import android.content.Context
import android.util.Log
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateIf
import androidx.glance.currentState
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig
import blblblbl.simplelife.widget.WidgetKeys.Prefs.cityNamePK
import blblblbl.simplelife.widget.WidgetKeys.Prefs.forecastJSONPK
import blblblbl.simplelife.widget.WidgetKeys.Prefs.weatherConfigPK
import blblblbl.simplelife.widget.content.WeatherWidgetContentLarge
import blblblbl.simplelife.widget.content.WeatherWidgetContentMedium
import blblblbl.simplelife.widget.content.WeatherWidgetContentSmall
import blblblbl.simplelife.widget.theme.WeatherWidgetTheme
import com.google.gson.GsonBuilder

class WeatherWidget :GlanceAppWidget(){

    companion object {
        private val smallMode = DpSize(120.dp, 120.dp)
        private val mediumMode = DpSize(260.dp, 200.dp)
        private val largeMode = DpSize(300.dp, 280.dp)
    }

    override var stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    override val sizeMode: SizeMode = SizeMode.Responsive(
        setOf(smallMode, mediumMode, largeMode)
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            WeatherWidgetTheme {
                val prefs = currentState<Preferences>()
                val size = LocalSize.current
                when (size){
                    smallMode-> WeatherWidgetContentSmall(prefs = prefs)
                    mediumMode-> WeatherWidgetContentMedium(prefs = prefs)
                    largeMode-> WeatherWidgetContentLarge(prefs = prefs)
                }
            }
        }
    }

}

class WeatherWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = WeatherWidget()
}

suspend fun GlanceAppWidgetManager.mapForecastToWidget(context: Context,forecastResponse: ForecastResponse) =

    getGlanceIds(WeatherWidget::class.java)
        .forEach { glanceId ->
            Log.d("MyLog","glanceId")
            updateAppWidgetState(context,glanceId){prefs->
                Log.d("MyLog","prefs[cityNamePK]:${prefs[cityNamePK]}")
                if (prefs[cityNamePK]==forecastResponse.location?.name){
                    val gson = GsonBuilder().setLenient().create()
                    prefs[forecastJSONPK] = gson.toJson(forecastResponse)
                }
            }
            WeatherWidget().updateIf<Preferences>(context){
                it[cityNamePK] == forecastResponse.location?.name
            }
        }

suspend fun GlanceAppWidgetManager.mapWeatherConfigToWidget(context: Context,weatherConfig: WeatherConfig) =
    getGlanceIds(WeatherWidget::class.java)
        .forEach { glanceId ->
            updateAppWidgetState(context,glanceId){prefs->
                val gson = GsonBuilder().setLenient().create()
                prefs[weatherConfigPK] = gson.toJson(weatherConfig)
            }
            WeatherWidget().update(context,glanceId)
        }