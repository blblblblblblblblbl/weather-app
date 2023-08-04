package blblblbl.simplelife.main_screen.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.forecast.domain.model.forecast.AirQuality
import blblblbl.simplelife.forecast.domain.model.forecast.Forecastday
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig

/**
 * @author Kirill Tolmachev 04.08.2023
 */

@Composable
fun DetailedDayInfo(
    modifier: Modifier = Modifier,
    forecastday: Forecastday,
    weatherConfig: WeatherConfig
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        forecastday.day?.let { day->
            DayBlock(
                modifier = Modifier.fillMaxWidth(),
                day = day,
                weatherConfig = weatherConfig
            )
        }
        forecastday.astro?.let {
            AstroBlock(
                modifier = Modifier.fillMaxWidth(),
                astro = it
            )
        }
        forecastday.day?.airQuality?.let {
            if (it!= AirQuality()){
                AirQualityBlock(
                    modifier = Modifier.fillMaxWidth(),
                    airQuality = it
                )
            }
        }
    }
}
