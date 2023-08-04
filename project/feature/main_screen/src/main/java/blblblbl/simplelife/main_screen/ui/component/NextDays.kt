package blblblbl.simplelife.main_screen.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.forecast.domain.model.forecast.Forecast
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig

/**
 * @author Kirill Tolmachev 04.08.2023
 */

@Composable
fun NextDays(
    modifier: Modifier = Modifier,
    forecast: Forecast,
    weatherConfig: WeatherConfig
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        forecast.forecastday.forEach { day ->
            DropDownCard(
                modifier = Modifier.fillMaxWidth(),
                header = {
                    DayHead(day = day, weatherConfig = weatherConfig)
                }
            ) {
                DayExpanded(
                    modifier = Modifier.padding(10.dp),
                    forecastday = day,
                    weatherConfig = weatherConfig
                )
            }
        }
    }
}