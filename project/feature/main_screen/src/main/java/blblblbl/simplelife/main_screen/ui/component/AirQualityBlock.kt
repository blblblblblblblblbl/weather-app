package blblblbl.simplelife.main_screen.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.forecast.domain.model.forecast.AirQuality
import blblblbl.simplelife.main_screen.R
import blblblbl.simplelife.main_screen.ui.format

/**
 * @author Kirill Tolmachev 04.08.2023
 */

@Composable
fun AirQualityBlock(
    modifier: Modifier = Modifier,
    airQuality: AirQuality,
){
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large
    ) {
        val textStyle = MaterialTheme.typography.bodyLarge
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = stringResource(id = R.string.air_quality))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    airQuality.co?.let { co->
                        Text(text = "CO ${co.format(2)}", style = textStyle)
                    }
                    airQuality.o3?.let { o3->
                        Text(text = "O3 ${o3.format(2)}", style = textStyle)
                    }
                    airQuality.pm2_5?.let { pm2_5->
                        Text(text = "PM2.5 ${pm2_5.format(2)}", style = textStyle)
                    }
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    airQuality.no2?.let { no2->
                        Text(text = "NO2 ${no2.format(2)}", style = textStyle)
                    }
                    airQuality.so2?.let { so2->
                        Text(text = "SO2 ${so2.format(2)}", style = textStyle)
                    }
                    airQuality.pm10?.let { pm10->
                        Text(text = "PM10 ${pm10.format(2)}", style = textStyle)
                    }
                }

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                airQuality.usEpaIndex?.let {
                    Text(text = "US EPA ${it}", style = textStyle)
                }
                airQuality.gbDefraIndex?.let {
                    Text(text = "GB DEFRA ${it}", style = textStyle)
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview(){}