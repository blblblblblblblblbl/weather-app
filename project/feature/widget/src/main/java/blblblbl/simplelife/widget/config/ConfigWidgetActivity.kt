package blblblbl.simplelife.widget.config

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.lifecycle.lifecycleScope
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig
import blblblbl.simplelife.widget.WeatherWidget
import blblblbl.simplelife.widget.WidgetKeys.Prefs.cityNamePK
import blblblbl.simplelife.widget.WidgetKeys.Prefs.forecastJSONPK
import blblblbl.simplelife.widget.WidgetKeys.Prefs.weatherConfigPK
import blblblbl.simplelife.widget.config.theme.WeatherTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConfigWidgetActivity : ComponentActivity()  {

    private val viewModel: ConfigWidgetActivityViewModel by viewModels()
    private var widgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private val result = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActivity()
        setContent {
            val systemUiController = rememberSystemUiController()
            WeatherTheme(
                configFlow = viewModel.getSettingsFlow()
            ){
                val appConfig by viewModel.getSettingsFlow().collectAsState()
                val useDarkIcons = !isSystemInDarkTheme()
                val color = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp)
                SideEffect {
                    systemUiController.setSystemBarsColor(color, darkIcons = useDarkIcons)
                }
                CitiesFragment(onClick = {name,forecast->
                    appConfig?.weatherConfig?.let {weatherConfig->
                        handleSelectCity(name,forecast,weatherConfig)
                    }
            })
            }

        }
    }

    private fun handleSelectCity(name:String, forecast: ForecastResponse,weatherConfig:WeatherConfig) {
        setResult(RESULT_OK, result)
        finish()
        saveWidgetState(name,forecast,weatherConfig)
    }

    private fun saveWidgetState(name:String, forecast: ForecastResponse,weatherConfig:WeatherConfig) = lifecycleScope.launch{
        val glanceId = GlanceAppWidgetManager(applicationContext).getGlanceIdBy(widgetId)
        updateAppWidgetState(applicationContext, glanceId) { prefs ->
            val gson = GsonBuilder().setLenient().create()
            prefs[cityNamePK] = name
            prefs[forecastJSONPK] = gson.toJson(forecast)
            prefs[weatherConfigPK] = gson.toJson(weatherConfig)
        }
        WeatherWidget().update(applicationContext, glanceId)
    }

    private fun setupActivity() {
        setResult(RESULT_CANCELED, result)
        getWidgetId()
        initResult()
    }

    private fun getWidgetId() {
        widgetId = intent.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: return
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) finish()
    }

    private fun initResult() = result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)

}