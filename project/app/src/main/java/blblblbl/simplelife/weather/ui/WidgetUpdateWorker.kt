package blblblbl.simplelife.weather.ui

import android.content.Context
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import blblblbl.simplelife.database.model.CityWeatherEntity
import blblblbl.simplelife.forecast.data.utils.mapToDomain
import blblblbl.simplelife.forecast.domain.usecase.GetForecastUseCase
import blblblbl.simplelife.weather.di.database.DataBaseCreator
import blblblbl.simplelife.weather.di.database.main.mapToDB
import blblblbl.simplelife.widget.WeatherWidget
import blblblbl.simplelife.widget.WidgetKeys.Prefs.cityNamePK
import blblblbl.simplelife.widget.di.update.UpdateWeatherWidget
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@HiltWorker
class WidgetUpdateWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val getForecastUseCase: GetForecastUseCase,
    private val updateWeatherWidget: UpdateWeatherWidget
): Worker(context,params){
    override fun doWork(): Result {
        return try {
            Log.d("MyLog","doWork")
            runBlocking {
                val glanceIds = GlanceAppWidgetManager(context).getGlanceIds(WeatherWidget::class.java)
                glanceIds.forEach { glanceId ->
                    updateAppWidgetState(context,glanceId){prefs->
                        val cityName = prefs[cityNamePK]
                        cityName?.let { cityName->
                            Log.d("MyLog","updateWeatherWidget, city:$cityName")
                            val forecast = getForecastUseCase.getForecastByName(cityName,7,"no","no")
                            Log.d("MyLog","forecast $forecast")
                            updateWeatherWidget.updateForecast(context,forecast)
                            Log.d("MyLog","updateWeatherWidget, city:$cityName")
                        }
                    }
                }
            }
            Result.success()
        }
        catch (e:Throwable){
            Log.d("MyLog","update worker exception:${e.message}")
            Result.failure()
        }

    }

}