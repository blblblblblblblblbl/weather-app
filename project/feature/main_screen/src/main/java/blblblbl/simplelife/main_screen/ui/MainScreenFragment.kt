package blblblbl.simplelife.main_screen.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import blblblbl.simplelife.forecast.domain.model.Current
import blblblbl.simplelife.forecast.domain.model.Forecast
import blblblbl.simplelife.forecast.domain.model.ForecastResponse
import blblblbl.simplelife.main_screen.R
import blblblbl.simplelife.main_screen.presentation.MainScreenFragmentViewModel
import com.google.android.material.datepicker.DateSelector
import com.skydoves.landscapist.glide.GlideImage
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenFragment() {
    val viewModel = hiltViewModel<MainScreenFragmentViewModel>()
    val forecast by viewModel.forecast.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val context = LocalContext.current
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            Box(modifier = Modifier.padding(10.dp)) {
                SearchWidget(
                    text = searchQuery,
                    onTextChange = {
                        viewModel.updateSearchQuery(query = it)
                    },
                    onSearchClicked = {
                        viewModel.getForecast(context)
                    },
                    onCloseClicked = {
                        viewModel.updateSearchQuery("")
                    }
                )
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.MyLocation, contentDescription = "location button")
                    }
                }
                forecast?.let { forecast ->
                    CurrentWeatherBlock(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        forecast = forecast
                    )
                }
                forecast?.forecast?.let { nextDays ->
                    NextDays(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        forecast = nextDays
                    )
                }
            }
        }
    }
}

@Composable
fun CurrentWeatherBlock(
    modifier: Modifier = Modifier,
    forecast: ForecastResponse
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                forecast.location?.name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
                forecast.current?.tempC?.let {
                    Text(
                        text = "${it}°C",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    forecast.current?.condition?.text?.let { Text(text = it) }
                    forecast.current?.condition?.icon?.let {
                        GlideImage(
                            imageModel = { "https:" + it },
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }
            }
            forecast.current?.windKph?.let { windSpeed ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "wind")
                    Text(text = "${windSpeed} km/h")
                }
            }
            forecast.current?.humidity?.let { humidity ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "humidity")
                    Text(text = "${humidity}%")
                }
            }
            forecast.current?.lastUpdated?.let { lastUpdated ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "last updated")
                    Text(text = lastUpdated)
                }
            }
        }
    }
}


@Composable
fun NextDays(
    modifier: Modifier = Modifier,
    forecast: Forecast
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
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        day.date?.let {
                            Text(text = "${it.subSequence(8, 10)}.${it.subSequence(5, 7)}")
                        }
                        day.day?.avgtempC?.let {
                            Icon(
                                painter = painterResource(id = R.drawable.temperature_icon),
                                contentDescription = "temperature",
                                modifier = Modifier.requiredWidth(20.dp)
                            )
                            Text(text = "${it}°C")
                        }
                        day.day?.avgvisKm?.let { wind ->
                            Icon(
                                painter = painterResource(id = R.drawable.wind_icon),
                                contentDescription = "wind speed",
                                modifier = Modifier.requiredWidth(24.dp)
                            )
                            Text(text = "${wind} km/h")
                        }
                        day.day?.dailyChanceOfRain?.let { rain ->
                            Icon(
                                painter = painterResource(id = R.drawable.rain_icon),
                                contentDescription = "rain chance",
                                modifier = Modifier.requiredWidth(24.dp)
                            )
                            Text(text = "${rain}%")
                        }
                    }
                }
            ) {

            }
        }
    }

}