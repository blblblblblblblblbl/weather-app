package blblblbl.simplelife.widget


import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.padding
import androidx.glance.text.Text
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.widget.WidgetKeys.Prefs.cityNamePK
import blblblbl.simplelife.widget.WidgetKeys.Prefs.forecastJSONPK
import com.google.gson.GsonBuilder

@Composable
fun WeatherWidgetContentThin(prefs:Preferences){
    /*val gson = GsonBuilder().setLenient().create()
    val cityName = prefs[cityNamePK]?:"city"
    val forecast = gson.fromJson(prefs[forecastJSONPK].orEmpty(),ForecastResponse::class.java)
    Column(
        modifier = GlanceModifier
            .background(imageProvider = ImageProvider(R.drawable.widget_background))
            .appWidgetBackground()
            .padding(16.dp)
    ) {

        Text(text = cityName)
        Text(text = forecast.toString())
    }*/
    Text(text = "thin")

}
@Composable
fun WeatherWidgetContentSmall(prefs:Preferences){
    /*val gson = GsonBuilder().setLenient().create()
    val cityName = prefs[cityNamePK]?:"city"
    val forecast = gson.fromJson(prefs[forecastJSONPK].orEmpty(),ForecastResponse::class.java)
    Column(
        modifier = GlanceModifier
            .background(imageProvider = ImageProvider(R.drawable.widget_background))
            .appWidgetBackground()
            .padding(16.dp)
    ) {

        Text(text = cityName)
        Text(text = forecast.toString())
    }*/
    Text(text = "small")

}

@Composable
fun WeatherWidgetContentMedium(prefs:Preferences){
    /*val gson = GsonBuilder().setLenient().create()
    val cityName = prefs[cityNamePK]?:"city"
    val forecast = gson.fromJson(prefs[forecastJSONPK].orEmpty(),ForecastResponse::class.java)
    Column(
        modifier = GlanceModifier
            .background(imageProvider = ImageProvider(R.drawable.widget_background))
            .appWidgetBackground()
            .padding(16.dp)
    ) {

        Text(text = cityName)
        Text(text = forecast.toString())
    }*/
    Text(text = "medium")
}

@Composable
fun WeatherWidgetContentLarge(prefs:Preferences){
    /*val gson = GsonBuilder().setLenient().create()
    val cityName = prefs[cityNamePK]?:"city"
    val forecast = gson.fromJson(prefs[forecastJSONPK].orEmpty(),ForecastResponse::class.java)
    Column(
        modifier = GlanceModifier
            .background(imageProvider = ImageProvider(R.drawable.widget_background))
            .appWidgetBackground()
            .padding(16.dp)
    ) {

        Text(text = cityName)
        Text(text = forecast.toString())
    }*/
    Text(text = "large")
}