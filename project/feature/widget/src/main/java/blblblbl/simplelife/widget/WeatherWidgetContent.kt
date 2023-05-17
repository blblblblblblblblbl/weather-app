package blblblbl.simplelife.widget


import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.widget.WidgetKeys.Prefs.cityNamePK
import blblblbl.simplelife.widget.WidgetKeys.Prefs.forecastJSONPK
import blblblbl.simplelife.widget.theme.WeatherWidgetTheme
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.GsonBuilder

@Composable
fun WeatherWidgetContentThin(prefs: Preferences) {
    val gson = GsonBuilder().setLenient().create()
    val cityName = prefs[cityNamePK] ?: "city"
    val forecast = gson.fromJson(prefs[forecastJSONPK].orEmpty(), ForecastResponse::class.java)
    AppWidgetColumn() {
        TextStyle()
        Text(
            text = cityName, style = TextStyle(
                fontSize = 20.sp, color = GlanceTheme.colors.onBackground
            )
        )

        Row(
            horizontalAlignment = Alignment.Horizontal.Start,
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            var weatherIcon by remember { mutableStateOf<Bitmap?>(null) }
            forecast.current?.condition?.icon?.let{icon->
                val context = LocalContext.current
                val bitmap = Glide.with(context).asBitmap().load("https:$icon")
                Glide.with(context)
                    .asBitmap()
                    .load("https:$icon")
                    .into(object : CustomTarget<Bitmap>(){
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            weatherIcon = resource.trimBorders(Color.TRANSPARENT)
                        }
                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })
            }
            weatherIcon?.let {
                Image(
                    provider = ImageProvider(it),
                    contentDescription = "temperature",
                    modifier = GlanceModifier.size(48.dp)
                )
            }
            Spacer(GlanceModifier.size(height = 10.dp, width = 4.dp))
            forecast.current?.tempC?.let { temp ->
                Text(
                    text = "${temp}°C", style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = GlanceTheme.colors.onBackground
                    )

                )
            }
        }

        Row(
            horizontalAlignment = Alignment.Horizontal.Start,
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            Image(
                provider = ImageProvider(R.drawable.wind_icon),
                contentDescription = "wind speed",
                modifier = GlanceModifier.size(48.dp)
            )
            Spacer(GlanceModifier.size(height = 10.dp, width = 4.dp))
            forecast.current?.windKph?.let { wind ->
                Row() {
                    Text(
                        text = "${wind}", style = TextStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = GlanceTheme.colors.onBackground
                        )
                    )
                    Text(
                        text = "mph", style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = GlanceTheme.colors.onBackground
                        )
                    )
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
                modifier = GlanceModifier.size(48.dp)
            )
            Spacer(GlanceModifier.size(height = 10.dp, width = 4.dp))
            forecast.current?.humidity?.let { humidity ->
                Text(
                    text = "${humidity}%", style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = GlanceTheme.colors.onBackground
                    )

                )
            }
        }
        Row(modifier = GlanceModifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Horizontal.End) {
            forecast.current?.lastUpdated?.let { lastUpdated->
                val text = lastUpdated.removePrefix("last updated ").removeRange(0,11)
                Text(
                    text = text, style = TextStyle(
                        fontSize = 18.sp, color = GlanceTheme.colors.onBackground
                    )
                )
            }
        }
    }
}

@Composable
fun WeatherWidgetContentSmall(prefs: Preferences) {
    val gson = GsonBuilder().setLenient().create()
    val cityName = prefs[cityNamePK] ?: "city"
    val forecast = gson.fromJson(prefs[forecastJSONPK].orEmpty(), ForecastResponse::class.java)
    AppWidgetColumn() {
        Text(
            text = cityName, style = TextStyle(
                fontSize = 20.sp, color = GlanceTheme.colors.onBackground
            )
        )
        Text(text = "icon")
        forecast.current?.tempC?.let { temp ->
            Text(
                text = "${temp}°C", style = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = GlanceTheme.colors.onBackground
                )

            )
        }
        Text(text = "wind")
    }
}

@Composable
fun WeatherWidgetContentMedium(prefs: Preferences) {
    val gson = GsonBuilder().setLenient().create()
    val cityName = prefs[cityNamePK] ?: "city"
    val forecast = gson.fromJson(prefs[forecastJSONPK].orEmpty(), ForecastResponse::class.java)
    AppWidgetColumn() {
        Text(
            text = cityName, style = TextStyle(
                fontSize = 30.sp, color = GlanceTheme.colors.onBackground
            )
        )
        Row {
            Column() {
                var weatherIcon by remember { mutableStateOf<Bitmap?>(null) }
                forecast.current?.condition?.icon?.let{icon->
                    val context = LocalContext.current
                    val bitmap = Glide.with(context).asBitmap().load("https:$icon")
                    Glide.with(context)
                        .asBitmap()
                        .load("https:$icon")
                        .into(object : CustomTarget<Bitmap>(){
                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                weatherIcon = resource.trimBorders(Color.TRANSPARENT)
                            }
                            override fun onLoadCleared(placeholder: Drawable?) {}
                        })
                }
                weatherIcon?.let {
                    Image(
                        provider = ImageProvider(it),
                        contentDescription = "temperature",
                        modifier = GlanceModifier.size(64.dp)
                    )
                }
                forecast.current?.condition?.text?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = GlanceTheme.colors.onBackground
                        )
                    )
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
                        modifier = GlanceModifier.size(48.dp)
                    )
                    Spacer(GlanceModifier.size(height = 10.dp, width = 4.dp))
                    forecast.current?.tempC?.let { temp ->
                        Text(
                            text = "${temp}°C", style = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = GlanceTheme.colors.onBackground
                            )

                        )
                    }
                }
                Row(
                    horizontalAlignment = Alignment.Horizontal.Start,
                    verticalAlignment = Alignment.Vertical.CenterVertically
                ) {
                    Image(
                        provider = ImageProvider(R.drawable.wind_icon),
                        contentDescription = "wind speed",
                        modifier = GlanceModifier.size(48.dp)
                    )
                    Spacer(GlanceModifier.size(height = 10.dp, width = 4.dp))
                    forecast.current?.windKph?.let { wind ->
                        Row() {
                            Text(
                                text = "${wind}", style = TextStyle(
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GlanceTheme.colors.onBackground
                                )
                            )
                            Text(
                                text = "mph", style = TextStyle(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GlanceTheme.colors.onBackground
                                )
                            )
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
                        modifier = GlanceModifier.size(48.dp)
                    )
                    Spacer(GlanceModifier.size(height = 10.dp, width = 4.dp))
                    forecast.current?.humidity?.let { humidity ->
                        Text(
                            text = "${humidity}%", style = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = GlanceTheme.colors.onBackground
                            )

                        )
                    }
                }
            }
        }
        Row(modifier = GlanceModifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Horizontal.End) {
            forecast.current?.lastUpdated?.let { lastUpdated->
                val text = lastUpdated.removePrefix("last updated ").removeRange(0,11)
                Text(
                    text = text, style = TextStyle(
                        fontSize = 18.sp, color = GlanceTheme.colors.onBackground
                    )
                )
            }
        }
    }
}

@Composable
fun WeatherWidgetContentLarge(prefs: Preferences) {
    val gson = GsonBuilder().setLenient().create()
    val cityName = prefs[cityNamePK] ?: "city"
    val forecast = gson.fromJson(prefs[forecastJSONPK].orEmpty(), ForecastResponse::class.java)
    AppWidgetColumn() {
        Text(
            text = cityName, style = TextStyle(
                fontSize = 30.sp, color = GlanceTheme.colors.onBackground
            )
        )
        Row {
            Column() {
                var weatherIcon by remember { mutableStateOf<Bitmap?>(null) }
                forecast.current?.condition?.icon?.let{icon->
                    val context = LocalContext.current
                    val bitmap = Glide.with(context).asBitmap().load("https:$icon")
                    Glide.with(context)
                        .asBitmap()
                        .load("https:$icon")
                        .into(object : CustomTarget<Bitmap>(){
                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                weatherIcon = resource.trimBorders(Color.TRANSPARENT)
                            }
                            override fun onLoadCleared(placeholder: Drawable?) {}
                        })
                }
                weatherIcon?.let {
                    Image(
                        provider = ImageProvider(it),
                        contentDescription = "temperature",
                        modifier = GlanceModifier.size(64.dp)
                    )
                }
                forecast.current?.condition?.text?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = GlanceTheme.colors.onBackground
                        )
                    )
                }
            }
            Spacer(modifier = GlanceModifier.size(height = 10.dp, width = 20.dp))
            Column() {
                Row() {
                    Row(
                        horizontalAlignment = Alignment.Horizontal.Start,
                        verticalAlignment = Alignment.Vertical.CenterVertically
                    ) {
                        Image(
                            provider = ImageProvider(R.drawable.temperature_icon),
                            contentDescription = "temperature",
                            modifier = GlanceModifier.size(48.dp)
                        )
                        Spacer(GlanceModifier.size(height = 10.dp, width = 4.dp))
                        forecast.current?.tempC?.let { temp ->
                            Text(
                                text = "${temp}°C", style = TextStyle(
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GlanceTheme.colors.onBackground
                                )

                            )
                        }
                    }
                    Spacer(GlanceModifier.size(height = 10.dp, width = 6.dp))
                    Row(
                        horizontalAlignment = Alignment.Horizontal.Start,
                        verticalAlignment = Alignment.Vertical.CenterVertically
                    ) {
                        Image(
                            provider = ImageProvider(R.drawable.humidity_icon),
                            contentDescription = "humidity",
                            modifier = GlanceModifier.size(48.dp)
                        )
                        Spacer(GlanceModifier.size(height = 10.dp, width = 4.dp))
                        forecast.current?.humidity?.let { humidity ->
                            Text(
                                text = "${humidity}%", style = TextStyle(
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GlanceTheme.colors.onBackground
                                )

                            )
                        }
                    }
                }

                Row(
                    horizontalAlignment = Alignment.Horizontal.Start,
                    verticalAlignment = Alignment.Vertical.CenterVertically
                ) {
                    Image(
                        provider = ImageProvider(R.drawable.wind_icon),
                        contentDescription = "wind speed",
                        modifier = GlanceModifier.size(48.dp)
                    )
                    Spacer(GlanceModifier.size(height = 10.dp, width = 4.dp))
                    forecast.current?.windKph?.let { wind ->
                        Row() {
                            Text(
                                text = "${wind}", style = TextStyle(
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GlanceTheme.colors.onBackground
                                )
                            )
                            Text(
                                text = "mph", style = TextStyle(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GlanceTheme.colors.onBackground
                                )
                            )
                        }
                    }
                }


            }
        }
        LazyRow(content = )
        LazyColumn(content = )
        Row(modifier = GlanceModifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Horizontal.End) {
            forecast.current?.lastUpdated?.let { lastUpdated->
                val text = lastUpdated.removePrefix("last updated ").removeRange(0,11)
                Text(
                    text = text, style = TextStyle(
                        fontSize = 18.sp, color = GlanceTheme.colors.onBackground
                    )
                )
            }
        }
    }
}
fun Bitmap.trimBorders(color: Int): Bitmap {
    var startX = 0
    loop@ for (x in 0 until width) {
        for (y in 0 until height) {
            if (getPixel(x, y) != color) {
                startX = x
                break@loop
            }
        }
    }
    var startY = 0
    loop@ for (y in 0 until height) {
        for (x in 0 until width) {
            if (getPixel(x, y) != color) {
                startY = y
                break@loop
            }
        }
    }
    var endX = width - 1
    loop@ for (x in endX downTo 0) {
        for (y in 0 until height) {
            if (getPixel(x, y) != color) {
                endX = x
                break@loop
            }
        }
    }
    var endY = height - 1
    loop@ for (y in endY downTo 0) {
        for (x in 0 until width) {
            if (getPixel(x, y) != color) {
                endY = y
                break@loop
            }
        }
    }

    val newWidth = endX - startX + 1
    val newHeight = endY - startY + 1

    return Bitmap.createBitmap(this, startX, startY, newWidth, newHeight)
}