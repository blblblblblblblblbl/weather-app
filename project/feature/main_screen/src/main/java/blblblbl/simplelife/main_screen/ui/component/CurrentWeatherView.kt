package blblblbl.simplelife.main_screen.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.forecast.utils.forecastResponseForPreview
import blblblbl.simplelife.main_screen.R
import blblblbl.simplelife.main_screen.ui.speedInUnits
import blblblbl.simplelife.main_screen.ui.temperatureInUnits
import blblblbl.simplelife.settings.domain.model.config.weather.DegreeUnit
import blblblbl.simplelife.settings.domain.model.config.weather.SpeedUnit
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig
import com.skydoves.landscapist.glide.GlideImage

/**
 * @author Kirill Tolmachev 04.08.2023
 */

@Composable
fun CurrentWeatherView(
    forecast: ForecastResponse,
    refreshOnClick: () -> Unit,
    weatherConfig: WeatherConfig,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            forecast.location?.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
            }
            forecast.current?.tempC?.let {
                Text(
                    text = temperatureInUnits(it, weatherConfig.degreeUnit),
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            forecast.current?.condition?.icon?.let {
                GlideImage(
                    imageModel = { "https:" + it },
                    previewPlaceholder = R.drawable.condition_icon_preview,
                    modifier = Modifier.size(64.dp)
                )
            }
            forecast.current?.condition?.text?.let { Text(text = it, textAlign = TextAlign.Center) }

            forecast.current?.windKph?.let { windSpeed ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = stringResource(id = R.string.wind))
                    Text(text = speedInUnits(windSpeed, weatherConfig.speedUnit))
                }
            }
            forecast.current?.humidity?.let { humidity ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = stringResource(id = R.string.humidity))
                    Text(text = "${humidity}%")
                }
            }
            forecast.current?.lastUpdated?.let { lastUpdated ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stringResource(id = R.string.updated))
                        Text(text = "${lastUpdated.subSequence(8, 10)}.${lastUpdated.subSequence(5, 7)} " + lastUpdated.split(" ")[1])
                    }
                    IconButton(onClick = { refreshOnClick() }) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "refresh",
                            Modifier.size(64.dp)
                        )
                    }
                }
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
    CurrentWeatherView(
        forecast = forecast,
        refreshOnClick = { /*TODO*/ },
        weatherConfig = weatherConfig)
}



