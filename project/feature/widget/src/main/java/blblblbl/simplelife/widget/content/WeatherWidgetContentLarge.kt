package blblblbl.simplelife.widget.content

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.wrapContentHeight
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import blblblbl.simplelife.forecast.domain.model.forecast.Current
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.forecast.domain.model.forecast.Forecastday
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig
import blblblbl.simplelife.widget.AppWidgetColumn
import blblblbl.simplelife.widget.R
import blblblbl.simplelife.widget.WidgetKeys
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.GsonBuilder

@Composable
fun WeatherWidgetContentLarge(prefs: Preferences) {
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
                forecast?.current?.let {
                    CurrentBlock(current = it, weatherConfig = weatherConfig)
                }
            }
            Spacer(modifier = GlanceModifier.size(height = 10.dp, width = 20.dp))
            forecast.forecast?.forecastday?.get(0)?.let { day->
                HoursBlock(day = day,weatherConfig)
            }
        }

    }
}
@Composable
fun CurrentBlock(
    current:Current,
    weatherConfig: WeatherConfig
){
    val bigTextStyle = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = GlanceTheme.colors.onBackground
    )
    Column() {
        current.condition?.text?.let {
            Text(text = it, style = bigTextStyle.copy(fontSize = 20.sp))
        }
        Row(
            horizontalAlignment = Alignment.Horizontal.Start,
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            current.condition?.icon?.let{icon->
                WeatherIcon(url = "https:$icon", modifier = GlanceModifier.size(64.dp))
            }
            Spacer(GlanceModifier.size(height = 10.dp, width = 4.dp))
            current.lastUpdated?.let { lastUpdated->
                val text = lastUpdated.removePrefix("last updated ").removeRange(0,11)
                Text(text = text, style = bigTextStyle)
            }
        }

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
            current.tempC?.let { temp ->
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
            current.windKph?.let { wind ->
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
            current.humidity?.let { humidity ->
                Text(text = "${humidity}%", style = bigTextStyle)
            }
        }
    }
}
@Composable
fun HoursBlock(day:Forecastday,weatherConfig: WeatherConfig){
    LazyColumn(){
        items(day.hour){hour->
            Box(
                modifier = GlanceModifier
                    .padding(vertical = 4.dp)
                    .background(GlanceTheme.colors.background)
            ) {
                Box(
                    modifier = GlanceModifier
                        .padding(4.dp)
                        .appWidgetBackground()
                        .background(GlanceTheme.colors.primaryContainer)
                        .cornerRadius(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.Vertical.CenterVertically
                    ) {
                        hour.time?.let {
                            Text(
                                text = it.substring(11),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GlanceTheme.colors.onPrimaryContainer
                                )
                            )
                        }
                        hour.condition?.icon?.let{icon->
                            WeatherIcon(url = "https:$icon", modifier = GlanceModifier.size(48.dp))
                        }
                        hour.tempC?.let { temp->
                            Text(
                                text = temepatureInUnits(temp,weatherConfig.degreeUnit), style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GlanceTheme.colors.onPrimaryContainer
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
