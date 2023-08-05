package blblblbl.simplelife.main_screen.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.forecast.domain.model.forecast.Day
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
fun DayView(
    day: Day,
    weatherConfig: WeatherConfig,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            day.condition?.text?.let {
                Text(text = it, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TemperatureAndCondition(
                    iconUrl = day.condition?.icon,
                    avgtempC = day.avgtempC,
                    mintempC = day.mintempC,
                    maxtempC = day.maxtempC,
                    weatherConfig = weatherConfig
                )
                WeatherParameters(
                    maxwindKph = day.maxwindKph,
                    avghumidity = day.avghumidity,
                    uv = day.uv,
                    weatherConfig = weatherConfig
                )
            }
        }
    }
}

@Composable
private fun TemperatureAndCondition(
    iconUrl: String?,
    avgtempC: Double?,
    mintempC: Double?,
    maxtempC: Double?,
    weatherConfig: WeatherConfig
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        iconUrl.let {
            GlideImage(
                imageModel = { "https:" + it },
                previewPlaceholder = R.drawable.condition_icon_preview,
                modifier = Modifier.size(64.dp)
            )
        }
        avgtempC?.let {
            Text(
                text = temperatureInUnits(it, weatherConfig.degreeUnit),
                style = MaterialTheme.typography.headlineLarge
            )
        }
        val minTemp = mintempC
        val maxTemp = maxtempC
        if (minTemp != null && maxTemp != null) {
            val minString = temperatureInUnits(minTemp, weatherConfig.degreeUnit)
            val maxString = temperatureInUnits(maxTemp, weatherConfig.degreeUnit)
            Text(text = "${minString.subSequence(0, minString.lastIndex - 1)}...${maxString}")
        }
    }
}

@Composable
private fun WeatherParameters(
    maxwindKph: Double?,
    avghumidity: Int?,
    uv: Double?,
    weatherConfig: WeatherConfig
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        maxwindKph?.let {
            WeatherParameter(
                name = stringResource(id = R.string.max_wind),
                value = speedInUnits(it, weatherConfig.speedUnit)
            )
        }
        avghumidity?.let {
            WeatherParameter(name = stringResource(id = R.string.humidity), value = "${it}")
        }
        uv.let {
            WeatherParameter(name = stringResource(id = R.string.uv), value = "${it}")
        }
    }
}

@Composable
private fun WeatherParameter(
    name: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(text = name)
        Text(text = value)
    }
}

@Preview
@Composable
private fun Preview() {
    val context = LocalContext.current
    val forecast = forecastResponseForPreview(context)
    val weatherConfig = WeatherConfig(DegreeUnit.C, SpeedUnit.Ms)
    val day = forecast.forecast?.forecastday?.first()?.day
    day?.let {
        DayView(day = it, weatherConfig = weatherConfig)
    }

}
