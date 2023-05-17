package blblblbl.simplelife.widget

import android.content.Context
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
import blblblbl.simplelife.widget.WidgetKeys.Prefs.cityNamePK
import blblblbl.simplelife.widget.WidgetKeys.Prefs.forecastJSONPK
import com.google.gson.GsonBuilder

class WeatherWidget :GlanceAppWidget(){

    companion object {
        private val thinMode = DpSize(120.dp, 120.dp)
        private val smallMode = DpSize(184.dp, 184.dp)
        private val mediumMode = DpSize(260.dp, 200.dp)
        private val largeMode = DpSize(260.dp, 280.dp)
    }

    override var stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    override val sizeMode: SizeMode = SizeMode.Responsive(
        setOf(thinMode, smallMode, mediumMode, largeMode)
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val prefs = currentState<Preferences>()
            val size = LocalSize.current
            when (size){
                thinMode-> WeatherWidgetContentThin(prefs = prefs)
                smallMode-> WeatherWidgetContentSmall(prefs = prefs)
                mediumMode-> WeatherWidgetContentMedium(prefs = prefs)
                largeMode-> WeatherWidgetContentLarge(prefs = prefs)
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
            updateAppWidgetState(context,glanceId){prefs->
                if (prefs[cityNamePK]==forecastResponse.location?.name){
                    val gson = GsonBuilder().setLenient().create()
                    prefs[forecastJSONPK] = gson.toJson(forecastResponse)
                }
            }
            WeatherWidget().updateIf<Preferences>(context){
                it[cityNamePK] == forecastResponse.location?.name
            }
        }