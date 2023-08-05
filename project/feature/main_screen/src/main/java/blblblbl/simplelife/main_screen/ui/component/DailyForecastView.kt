package blblblbl.simplelife.main_screen.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.coreutils.utils.compose.DropDownCard
import blblblbl.simplelife.forecast.domain.model.forecast.Forecast
import blblblbl.simplelife.forecast.utils.forecastResponseForPreview
import blblblbl.simplelife.settings.domain.model.config.weather.DegreeUnit
import blblblbl.simplelife.settings.domain.model.config.weather.SpeedUnit
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig

/**
 * @author Kirill Tolmachev 04.08.2023
 */

@Composable
fun DailyForecastView(
    modifier: Modifier = Modifier,
    forecast: Forecast,
    weatherConfig: WeatherConfig
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        forecast.forecastday.forEach { day ->
            DropDownCard(
                modifier = Modifier.fillMaxWidth(),
                header = {
                    DayHeadView(
                        day = day,
                        weatherConfig = weatherConfig,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                }
            ) {
                DayExpandedView(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    forecastday = day,
                    weatherConfig = weatherConfig
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val context = LocalContext.current
    val forecastResponse = forecastResponseForPreview(context)
    val weatherConfig = WeatherConfig(DegreeUnit.C, SpeedUnit.Ms)
    forecastResponse.forecast?.let {
        DailyForecastView(forecast = it, weatherConfig = weatherConfig)
    }
}