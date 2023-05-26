package blblblbl.simplelife.main_screen.ui

import android.Manifest
import android.content.Context
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.location.LocationManagerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import blblblbl.simplelife.forecast.domain.model.forecast.Astro
import blblblbl.simplelife.forecast.domain.model.forecast.Day
import blblblbl.simplelife.forecast.domain.model.forecast.Forecast
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.forecast.domain.model.forecast.Forecastday
import blblblbl.simplelife.forecast.domain.model.forecast.Hour
import blblblbl.simplelife.main_screen.R
import blblblbl.simplelife.main_screen.presentation.LoadingState
import blblblbl.simplelife.main_screen.presentation.MainScreenFragmentViewModel
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.skydoves.landscapist.glide.GlideImage


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen() {
    val viewModel = hiltViewModel<MainScreenFragmentViewModel>()
    val forecast by viewModel.forecast.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val appConfig by viewModel.settings.collectAsState()
    val loadState by viewModel.loadState.collectAsState()
    val isInFavourites by viewModel.isInFavourites.collectAsState()
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
                        viewModel.getForecastByName(context)
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
                    LocationButton {
                        viewModel.locationOnClick(context)
                    }
                }
                appConfig?.weatherConfig?.let { weatherConfig ->
                    forecast?.let { forecast ->
                        CurrentWeatherBlock(
                            modifier = Modifier.fillMaxWidth(0.7f),
                            forecast = forecast,
                            refreshOnClick = {viewModel.refresh(context)},
                            weatherConfig = weatherConfig
                        )
                    }
                    if (!isInFavourites){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(onClick = {
                                viewModel.saveForecastToFavourites()
                            }) {
                                Icon(Icons.Default.Favorite, contentDescription = "location button")
                            }
                        }
                    }
                    forecast?.forecast?.let { nextDays ->
                        NextDays(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            forecast = nextDays,
                            weatherConfig =weatherConfig
                        )
                    }
                }

            }
            if (loadState == LoadingState.LOADING){
                Surface(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.fillMaxSize()){
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }

}
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationButton(
    onClick: () -> Unit
){
    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ),
        onPermissionsResult = {
            val notGrantedList = it.values.filter {!it}
            if (notGrantedList.isEmpty()){
                onClick()
            }
        }
    )
    IconButton(onClick = {
        if (locationPermissionState.allPermissionsGranted){
            onClick()
        }
        else{
            locationPermissionState.launchMultiplePermissionRequest()
        }
    }) {
        Icon(Icons.Default.MyLocation, contentDescription = "location button")
    }
}
@Composable
fun CurrentWeatherBlock(
    modifier: Modifier = Modifier,
    forecast: ForecastResponse,
    refreshOnClick:()->Unit,
    weatherConfig: WeatherConfig
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
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center
                    )
                }
                forecast.current?.tempC?.let {
                    Text(
                        text = temepatureInUnits(it,weatherConfig.degreeUnit),
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
                    Text(text = speedInUnits(windSpeed,weatherConfig.speedUnit))
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "last updated")
                        Text(text = "${lastUpdated.subSequence(8, 10)}.${lastUpdated.subSequence(5, 7)} "+lastUpdated.split(" ")[1])
                    }
                    IconButton(onClick = { refreshOnClick() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "refresh")
                    }
                }
            }
        }
    }
}


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
@Composable
fun DayHead(
    modifier: Modifier = Modifier,
    day: Forecastday,
    weatherConfig:WeatherConfig
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
            Icon(
                painter = painterResource(id = R.drawable.temperature_icon),
                contentDescription = "temperature",
                modifier = Modifier.requiredWidth(20.dp)
            )
            Text(text = temepatureInUnits(it,weatherConfig.degreeUnit))
        }
        day.day?.maxwindKph?.let { wind ->
            Icon(
                painter = painterResource(id = R.drawable.wind_icon),
                contentDescription = "wind speed",
                modifier = Modifier.requiredWidth(24.dp)
            )
            Text(text = speedInUnits(wind,weatherConfig.speedUnit))
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

@Composable
fun DayExpanded(
    modifier: Modifier = Modifier,
    forecastday: Forecastday,
    weatherConfig: WeatherConfig
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            forecastday.hour.forEach {hour->
                HourCard(hour = hour, weatherConfig =weatherConfig )
            }
        }
        DetailedDayInfo(forecastday = forecastday, weatherConfig = weatherConfig)
    }
}

@Composable
fun HourCard(
    modifier: Modifier = Modifier,
    hour: Hour,
    weatherConfig: WeatherConfig
){
    Card(
        modifier = modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.large
            ),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            hour.time?.let {
                Text(text = it.substring(11))
            }
            hour.tempC?.let {
                Text(
                    text = temepatureInUnits(it,weatherConfig.degreeUnit),
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            hour.condition?.icon?.let {
                GlideImage(
                    imageModel = { "https:" + it },
                    modifier = Modifier.size(48.dp)
                )
            }
            hour.windKph?.let {wind->
                Icon(
                    painter = painterResource(id = R.drawable.wind_icon),
                    contentDescription = "wind speed",
                    modifier = Modifier.requiredHeight(24.dp)
                )
                Text(text = speedInUnits(wind,weatherConfig.speedUnit))
            }
            hour.chanceOfRain?.let { rain ->
                Icon(
                    painter = painterResource(id = R.drawable.rain_icon),
                    contentDescription = "rain chance",
                    modifier = Modifier.requiredHeight(24.dp)
                )
                Text(text = "${rain}%")
            }
        }
    }
}

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
    }

}
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    day?.condition?.icon?.let {
                        GlideImage(
                            imageModel = { "https:" + it },
                            modifier = Modifier.size(64.dp)
                        )
                    }
                    day?.condition?.text?.let{
                        Text(text = it, style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp))
                    }
                    day?.avgtempC?.let {
                        Text(
                            text = temepatureInUnits(it,weatherConfig.degreeUnit),
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                    val minTemp = day?.mintempC
                    val maxTemp = day?.maxtempC
                    if (minTemp!=null&&maxTemp!=null){
                        val minString = temepatureInUnits(minTemp,weatherConfig.degreeUnit)
                        val maxString = temepatureInUnits(maxTemp,weatherConfig.degreeUnit)
                        Text(text = "${minString.subSequence(0,minString.lastIndex-1)}...${maxString}")
                    }
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    day?.maxwindKph?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "max wind    ")
                            Text(text = speedInUnits(it,weatherConfig.speedUnit))
                        }
                    }
                    day?.avghumidity?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "humidity    ")
                            Text(text = "${it}")
                        }
                    }
                    day?.uv?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "uv    ")
                            Text(text = "${it}")
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun AstroBlock(
    modifier: Modifier = Modifier,
    astro: Astro
){
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large
    ) {
       Row(
           horizontalArrangement = Arrangement.SpaceAround
       ) {
           Column() {
               val sunrise = astro.sunrise
               val sunset = astro.sunset
               if (sunrise!=null &&sunset!=null){
                   Row(
                       verticalAlignment = Alignment.CenterVertically,
                       horizontalArrangement = Arrangement.spacedBy(4.dp)
                   ) {
                       val iconSize = 48.dp
                       val textSize = 12.sp
                       Column(
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {
                           Icon(painterResource(id = R.drawable.sunrise), contentDescription = "sunrise", modifier = Modifier.size(iconSize))
                           Text(text = sunrise, style = MaterialTheme.typography.bodySmall.copy(fontSize = textSize) )
                       }
                       Column(
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {
                           Icon(painterResource(id = R.drawable.sunset), contentDescription = "sunset", modifier = Modifier.size(iconSize))
                           Text(text = sunset, style = MaterialTheme.typography.bodySmall.copy(fontSize = textSize))
                       }
                   }
               }
               val moonrise = astro.moonrise
               val moonset = astro.moonset
               if (moonrise!=null &&moonset!=null){
                   Row(
                       verticalAlignment = Alignment.CenterVertically,
                       horizontalArrangement = Arrangement.spacedBy(4.dp)
                   ) {
                       val iconSize = 48.dp
                       val textSize = 12.sp
                       Column(
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {
                           Icon(painterResource(id = R.drawable.moonrise), contentDescription = "moonrise", modifier = Modifier.size(iconSize))
                           Text(text = moonrise, style = MaterialTheme.typography.bodySmall.copy(fontSize = textSize))
                       }
                       Column(
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {
                           Icon(painterResource(id = R.drawable.moonset), contentDescription = "moonset", modifier = Modifier.size(iconSize))
                           Text(text = moonset, style = MaterialTheme.typography.bodySmall.copy(fontSize = textSize))
                       }
                   }
               }
           }
           Column(horizontalAlignment = Alignment.CenterHorizontally) {
               astro.moonPhase?.let { moonPhase->
                   Text(text = moonPhase)
                   val iconSize = 64.dp
                   when(moonPhase){
                       "New Moon"-> Icon(painterResource(id = R.drawable.new_moon), contentDescription = "New Moon", modifier = Modifier.size(iconSize))
                       "Waxing Crescent"-> Icon(painterResource(id = R.drawable.waning_crescent), contentDescription = "Waxing Crescent", modifier = Modifier.size(iconSize))
                       "First Quarter"-> Icon(painterResource(id = R.drawable.first_quarter), contentDescription = "First Quarter", modifier = Modifier.size(iconSize))
                       "Waxing Gibbous"-> Icon(painterResource(id = R.drawable.waxing_gibbous), contentDescription = "Waxing Gibbous", modifier = Modifier.size(iconSize))
                       "Full Moon"-> Icon(painterResource(id = R.drawable.full_moon), contentDescription = "Full Moon", modifier = Modifier.size(iconSize))
                       "Waning Gibbous"-> Icon(painterResource(id = R.drawable.waning_gibbous), contentDescription = "Waning Gibbous", modifier = Modifier.size(iconSize))
                       "Last Quarter"-> Icon(painterResource(id = R.drawable.last_quarter), contentDescription = "Last Quarter", modifier = Modifier.size(iconSize))
                       "Waning Crescent"-> Icon(painterResource(id = R.drawable.waning_crescent), contentDescription = "Waning Crescent", modifier = Modifier.size(iconSize))
                   }
               }
               astro.moonIllumination?.let {
                   Text(text = "illumination  ${it}%")
               }
           }
       }
    }
}