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
fun AirQualityView(
    airQuality: AirQuality,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(6.dp),
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
                    airQuality.co?.let { co -> AirQualityText("CO ${co.format(2)}") }
                    airQuality.o3?.let { o3 -> AirQualityText("O3 ${o3.format(2)}") }
                    airQuality.pm2_5?.let { pm2_5 -> AirQualityText("PM2.5 ${pm2_5.format(2)}") }
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    airQuality.no2?.let { no2 -> AirQualityText("NO2 ${no2.format(2)}") }
                    airQuality.so2?.let { so2 -> AirQualityText("SO2 ${so2.format(2)}") }
                    airQuality.pm10?.let { pm10 -> AirQualityText("PM10 ${pm10.format(2)}") }
                }

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                airQuality.usEpaIndex?.let { usEpaIndex -> AirQualityText("US EPA ${usEpaIndex}") }
                airQuality.gbDefraIndex?.let { gbDefraIndex -> AirQualityText("GB DEFRA ${gbDefraIndex}") }
            }
        }
    }
}

@Composable
private fun AirQualityText(
    text: String
) {
    Text(text = text, style = MaterialTheme.typography.bodyLarge)
}

@Preview
@Composable
private fun Preview() {
    val airQuality = AirQuality(
        co = 15.2,
        no2 = 15.2,
        gbDefraIndex = 15,
        o3 = 15.2,
        pm2_5 = 15.2,
        pm10 = 15.2,
        so2 = 15.2,
        usEpaIndex = 15
    )
    AirQualityView(airQuality = airQuality)
}