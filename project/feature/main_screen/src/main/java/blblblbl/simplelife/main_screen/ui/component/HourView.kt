package blblblbl.simplelife.main_screen.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.forecast.domain.model.forecast.Hour
import blblblbl.simplelife.forecast.utils.forecastResponseForPreview
import blblblbl.simplelife.main_screen.R
import blblblbl.simplelife.main_screen.ui.speedInUnits
import blblblbl.simplelife.main_screen.ui.temperatureInUnits
import blblblbl.simplelife.settings.domain.model.config.weather.DegreeUnit
import blblblbl.simplelife.settings.domain.model.config.weather.SpeedUnit
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig
import com.skydoves.landscapist.glide.GlideImage

/**
 * @author Kirill Tolmachev 04.08.2023
 */

@Composable
fun HourView(
    hour: Hour,
    weatherConfig: WeatherConfig,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.large
            )
            .clip(MaterialTheme.shapes.large)
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CollapsedView(hour, weatherConfig)
            AnimatedVisibility(visible = expanded) {
                ExpandedView(hour)
            }
        }
    }
}

@Composable
private fun CollapsedView(
    hour: Hour,
    weatherConfig: WeatherConfig,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        hour.time?.let {
            Text(text = it.substring(11))
        }
        hour.tempC?.let {
            Text(
                text = temperatureInUnits(it, weatherConfig.degreeUnit),
                style = MaterialTheme.typography.headlineLarge
            )
        }
        hour.condition?.icon?.let {
            GlideImage(
                imageModel = { "https:" + it },
                previewPlaceholder = R.drawable.condition_icon_preview,
                modifier = Modifier.size(48.dp)
            )
        }
        hour.windKph?.let { wind ->
            Icon(
                painter = painterResource(id = R.drawable.wind_icon),
                contentDescription = "wind speed",
                modifier = Modifier.requiredHeight(24.dp)
            )
            Text(text = speedInUnits(wind, weatherConfig.speedUnit))
        }
        hour.chanceOfRain?.let { rain ->
            Icon(
                painter = painterResource(id = R.drawable.rain_icon),
                contentDescription = "rain chance",
                modifier = Modifier.requiredHeight(24.dp)
            )
            Text(text = "${rain}%")
        }
    }
}

@Composable
private fun ExpandedView(
    hour: Hour,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        hour.humidity?.let {
            Icon(
                painter = painterResource(id = R.drawable.humidity_icon),
                contentDescription = "humidity",
                modifier = Modifier.size(44.dp)
            )
            Text(text = "$it%")
        }
        hour.windDir?.let {
            Icon(
                painter = painterResource(id = R.drawable.wind_direction_icon),
                contentDescription = "wind direction",
                modifier = Modifier.size(44.dp)
            )
            Text(text = it)
        }
        hour.uv?.let {
            Icon(
                painter = painterResource(id = R.drawable.uv_icon),
                contentDescription = "uv",
                modifier = Modifier.size(44.dp)
            )
            Text(text = it.toString())
        }
    }
}


@Preview
@Composable
private fun Preview() {
    val context = LocalContext.current
    val forecast = forecastResponseForPreview(context)
    val weatherConfig = WeatherConfig(DegreeUnit.C, SpeedUnit.Ms)
    val hour = forecast.forecast?.forecastday?.first()?.hour?.first()
    hour?.let {
        HourView(hour = hour, weatherConfig = weatherConfig)
    }
}
