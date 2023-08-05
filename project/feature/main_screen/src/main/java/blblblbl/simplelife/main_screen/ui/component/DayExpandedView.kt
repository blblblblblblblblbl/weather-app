package blblblbl.simplelife.main_screen.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.forecast.domain.model.forecast.Forecastday
import blblblbl.simplelife.forecast.utils.forecastResponseForPreview
import blblblbl.simplelife.settings.domain.model.config.weather.DegreeUnit
import blblblbl.simplelife.settings.domain.model.config.weather.SpeedUnit
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig

/**
 * @author Kirill Tolmachev 04.08.2023
 */

@Composable
fun DayExpandedView(
    modifier: Modifier = Modifier,
    forecastday: Forecastday,
    weatherConfig: WeatherConfig
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(forecastday.hour) { hour ->
                HourView(hour = hour, weatherConfig = weatherConfig)
            }
        }
        DayDetailedView(forecastday = forecastday, weatherConfig = weatherConfig)
    }
}

@Preview
@Composable
private fun Preview() {
    val context = LocalContext.current
    val forecast = forecastResponseForPreview(context)
    val weatherConfig = WeatherConfig(DegreeUnit.C, SpeedUnit.Ms)
    val forecastday = forecast.forecast?.forecastday?.first()
    forecastday?.let {
        DayExpandedView(forecastday = it, weatherConfig = weatherConfig)
    }
}
