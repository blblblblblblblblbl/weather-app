package blblblbl.simplelife.widget

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateIf
import androidx.glance.currentState
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.widget.WidgetKeys.Prefs.cityNamePK
import blblblbl.simplelife.widget.WidgetKeys.Prefs.forecastJSONPK
import com.google.gson.GsonBuilder

class WeatherWidget :GlanceAppWidget(){

    override var stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val prefs = currentState<Preferences>()
            WeatherWidgetContent(prefs)
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