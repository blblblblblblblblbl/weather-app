package blblblbl.simplelife.widget.content

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.size
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig
import blblblbl.simplelife.widget.AppWidgetColumn
import blblblbl.simplelife.widget.R
import blblblbl.simplelife.widget.WidgetKeys
import com.google.gson.GsonBuilder

@Composable
fun WeatherWidgetContentMedium(prefs: Preferences) {
    val gson = GsonBuilder().setLenient().create()
    val cityName = prefs[WidgetKeys.Prefs.cityNamePK] ?: "city"
    val forecast = gson.fromJson(prefs[WidgetKeys.Prefs.forecastJSONPK].orEmpty(), ForecastResponse::class.java)
    val weatherConfig = gson.fromJson(prefs[WidgetKeys.Prefs.weatherConfigPK].orEmpty(), WeatherConfig::class.java)
    val bigTextStyle = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = GlanceTheme.colors.onBackground
    )
    AppWidgetColumn() {
        Text(text = cityName, style = bigTextStyle)
        Row {
            Column() {
                forecast.current?.condition?.icon?.let{icon->
                    WeatherIcon(url = "https:$icon", modifier = GlanceModifier.size(64.dp))
                }
                forecast.current?.condition?.text?.let {
                    Text(text = it, style = bigTextStyle.copy(fontSize = 20.sp))
                }
            }
            Spacer(modifier = GlanceModifier.size(height = 10.dp, width = 20.dp))
            Column() {
                Row(
                    horizontalAlignment = Alignment.Horizontal.Start,
                    verticalAlignment = Alignment.Vertical.CenterVertically
                ) {

                    Image(
                        provider = ImageProvider(R.drawable.temperature_icon),
                        contentDescription = "temperature",
                        modifier = GlanceModifier.size(48.dp),
                        colorFilter = ColorFilter.tint(GlanceTheme.colors.onBackground)
                    )
                    Spacer(GlanceModifier.size(height = 10.dp, width = 4.dp))
                    forecast.current?.tempC?.let { temp ->
                        Text(text = temepatureInUnits(temp,weatherConfig.degreeUnit), style = bigTextStyle)
                    }
                }
                Row(
                    horizontalAlignment = Alignment.Horizontal.Start,
                    verticalAlignment = Alignment.Vertical.CenterVertically
                ) {
                    Image(
                        provider = ImageProvider(R.drawable.wind_icon),
                        contentDescription = "wind speed",
                        modifier = GlanceModifier.size(48.dp),
                        colorFilter = ColorFilter.tint(GlanceTheme.colors.onBackground)
                    )
                    Spacer(GlanceModifier.size(height = 10.dp, width = 4.dp))
                    forecast.current?.windKph?.let { wind ->
                        Row() {
                            val text = speedInUnits(wind,weatherConfig.speedUnit)
                            val strings = text.split("|")
                            Text(text = strings[0], style = bigTextStyle)
                            Text(text = strings[1], style = bigTextStyle.copy(fontSize = 24.sp))
                        }
                    }
                }
                Row(
                    horizontalAlignment = Alignment.Horizontal.Start,
                    verticalAlignment = Alignment.Vertical.CenterVertically
                ) {
                    Image(
                        provider = ImageProvider(R.drawable.humidity_icon),
                        contentDescription = "humidity",
                        modifier = GlanceModifier.size(48.dp),
                        colorFilter = ColorFilter.tint(GlanceTheme.colors.onBackground)
                    )
                    Spacer(GlanceModifier.size(height = 10.dp, width = 4.dp))
                    forecast.current?.humidity?.let { humidity ->
                        Text(text = "${humidity}%", style = bigTextStyle)
                    }
                }
            }
        }
        Row(modifier = GlanceModifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Horizontal.End) {
            forecast.current?.lastUpdated?.let { lastUpdated->
                val text = lastUpdated.removePrefix("last updated ").removeRange(0,11)
                Text(text = text, style = bigTextStyle.copy(fontSize = 18.sp))
            }
        }
    }
}