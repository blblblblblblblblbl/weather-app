package blblblbl.simplelife.main_screen.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.forecast.domain.model.forecast.Forecastday
import blblblbl.simplelife.main_screen.R
import blblblbl.simplelife.main_screen.ui.speedInUnits
import blblblbl.simplelife.main_screen.ui.temperatureInUnits
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig

/**
 * @author Kirill Tolmachev 04.08.2023
 */

@Composable
fun DayHead(
    modifier: Modifier = Modifier,
    day: Forecastday,
    weatherConfig: WeatherConfig
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        day.date?.let {
            Text(text = "${it.subSequence(8, 10)}.${it.subSequence(5, 7)}",fontWeight = FontWeight.Bold)
        }
        day.day?.avgtempC?.let {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.temperature_icon),
                    contentDescription = "temperature",
                    modifier = Modifier.requiredWidth(14.dp)
                )
                Text(text = temperatureInUnits(it,weatherConfig.degreeUnit))
            }

        }
        day.day?.maxwindKph?.let { wind ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(id = R.drawable.wind_icon),
                    contentDescription = "wind speed",
                    modifier = Modifier.requiredWidth(24.dp)
                )
                Text(text = speedInUnits(wind,weatherConfig.speedUnit))
            }
        }
        day.day?.dailyChanceOfRain?.let { rain ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(id = R.drawable.rain_icon),
                    contentDescription = "rain chance",
                    modifier = Modifier.requiredWidth(24.dp)
                )
                Text(text = "${rain}%")
            }
        }
    }
}
