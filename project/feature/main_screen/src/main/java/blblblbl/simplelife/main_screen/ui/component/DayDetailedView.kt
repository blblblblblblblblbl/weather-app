package blblblbl.simplelife.main_screen.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.forecast.domain.model.forecast.AirQuality
import blblblbl.simplelife.forecast.domain.model.forecast.Forecastday
import blblblbl.simplelife.forecast.utils.forecastResponseForPreview
import blblblbl.simplelife.settings.domain.model.config.weather.DegreeUnit
import blblblbl.simplelife.settings.domain.model.config.weather.SpeedUnit
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig

/**
 * @author Kirill Tolmachev 04.08.2023
 */

@Composable
fun DayDetailedView(
    forecastday: Forecastday,
    weatherConfig: WeatherConfig,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        forecastday.day?.let { day ->
            DayView(
                modifier = Modifier.fillMaxWidth(),
                day = day,
                weatherConfig = weatherConfig
            )
        }
        forecastday.astro?.let {
            AstroView(
                modifier = Modifier.fillMaxWidth(),
                astro = it
            )
        }
        forecastday.day?.airQuality?.let {
            if (it != AirQuality()) {
                AirQualityView(
                    modifier = Modifier.fillMaxWidth(),
                    airQuality = it
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val context = LocalContext.current
    val forecast = forecastResponseForPreview(context)
    val weatherConfig = WeatherConfig(DegreeUnit.C, SpeedUnit.Ms)
    val day = forecast.forecast?.forecastday?.first()
    day?.let {
        DayDetailedView(forecastday = it, weatherConfig = weatherConfig)
    }

}