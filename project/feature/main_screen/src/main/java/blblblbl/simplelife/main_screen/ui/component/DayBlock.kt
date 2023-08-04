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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.forecast.domain.model.forecast.Day
import blblblbl.simplelife.main_screen.R
import blblblbl.simplelife.main_screen.ui.speedInUnits
import blblblbl.simplelife.main_screen.ui.temperatureInUnits
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig
import com.skydoves.landscapist.glide.GlideImage

/**
 * @author Kirill Tolmachev 04.08.2023
 */

@Composable
fun DayBlock(
    modifier: Modifier = Modifier,
    day: Day,
    weatherConfig: WeatherConfig
){
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
            day.condition?.text?.let{
                Text(text =it, textAlign = TextAlign.Center,modifier= Modifier.fillMaxWidth())
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    day.condition?.icon?.let {
                        GlideImage(
                            imageModel = { "https:" + it },
                            modifier = Modifier.size(64.dp)
                        )
                    }
                    day.avgtempC?.let {
                        Text(
                            text = temperatureInUnits(it,weatherConfig.degreeUnit),
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                    val minTemp = day.mintempC
                    val maxTemp = day.maxtempC
                    if (minTemp!=null&&maxTemp!=null){
                        val minString = temperatureInUnits(minTemp,weatherConfig.degreeUnit)
                        val maxString = temperatureInUnits(maxTemp,weatherConfig.degreeUnit)
                        Text(text = "${minString.subSequence(0,minString.lastIndex-1)}...${maxString}")
                    }
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    day.maxwindKph?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Text(text = stringResource(id = R.string.max_wind))
                            Text(text = speedInUnits(it,weatherConfig.speedUnit))
                        }
                    }
                    day.avghumidity?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Text(text = stringResource(id = R.string.humidity))
                            Text(text = "${it}")
                        }
                    }
                    day.uv?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Text(text = stringResource(id = R.string.uv))
                            Text(text = "${it}")
                        }
                    }
                }
            }
        }
    }
}
