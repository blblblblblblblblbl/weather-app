package blblblbl.simplelife.main_screen.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.forecast.domain.model.forecast.Forecastday
import blblblbl.simplelife.forecast.utils.forecastResponseForPreview
import blblblbl.simplelife.main_screen.R
import blblblbl.simplelife.main_screen.ui.speedInUnits
import blblblbl.simplelife.main_screen.ui.temperatureInUnits
import blblblbl.simplelife.settings.domain.model.config.weather.DegreeUnit
import blblblbl.simplelife.settings.domain.model.config.weather.SpeedUnit
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig

/**
 * @author Kirill Tolmachev 04.08.2023
 */

@Composable
fun DayHeadView(
    day: Forecastday,
    weatherConfig: WeatherConfig,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        day.date?.let {
            Text(text = "${it.subSequence(8, 10)}.${it.subSequence(5, 7)}",fontWeight = FontWeight.Bold)
        }
        day.day?.avgtempC?.let {
            IconWithText(
                iconId = R.drawable.temperature_icon,
                iconSize = 14.dp,
                iconDescription = "temperature",
                text = temperatureInUnits(it,weatherConfig.degreeUnit)
            )
        }
        day.day?.maxwindKph?.let { wind ->
            IconWithText(
                iconId = R.drawable.wind_icon,
                iconSize = 24.dp,
                iconDescription = "wind speed",
                text = speedInUnits(wind,weatherConfig.speedUnit)
            )
        }
        day.day?.dailyChanceOfRain?.let { rain ->
            IconWithText(
                iconId = R.drawable.rain_icon,
                iconSize = 24.dp,
                iconDescription = "rain chance",
                text = "${rain}%"
            )
        }
    }
}

@Composable
private fun IconWithText(
    @DrawableRes iconId: Int,
    iconSize: Dp,
    iconDescription: String,
    text: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = iconDescription,
            modifier = Modifier.requiredWidth(iconSize)
        )
        Text(text = text)
    }
}

@Preview
@Composable
private fun DayHeadView() {
    val context = LocalContext.current
    val forecast = forecastResponseForPreview(context)
    val weatherConfig = WeatherConfig(DegreeUnit.C, SpeedUnit.Ms)
    val forecastday = forecast.forecast?.forecastday?.first()
    forecastday?.let {
        DayHeadView(day = it, weatherConfig = weatherConfig)
    }
}