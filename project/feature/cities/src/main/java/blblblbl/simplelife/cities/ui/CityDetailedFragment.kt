package blblblbl.simplelife.cities.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import blblblbl.simplelife.cities.R
import blblblbl.simplelife.cities.presentation.CityDetailedFragmentViewModel
import blblblbl.simplelife.cities.presentation.LoadingState
import blblblbl.simplelife.forecast.domain.model.forecast.AirQuality
import blblblbl.simplelife.forecast.domain.model.forecast.Alerts
import blblblbl.simplelife.forecast.domain.model.forecast.Astro
import blblblbl.simplelife.forecast.domain.model.forecast.Day
import blblblbl.simplelife.forecast.domain.model.forecast.Forecast
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.forecast.domain.model.forecast.Forecastday
import blblblbl.simplelife.forecast.domain.model.forecast.Hour
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDetailedFragment(cityName:String) {
    val viewModel = hiltViewModel<CityDetailedFragmentViewModel>()
    viewModel.getDBForecast(cityName)
    val forecast by viewModel.forecast.collectAsState()
    val appConfig by viewModel.settings.collectAsState()
    val loadState by viewModel.loadState.collectAsState()
    val error by viewModel.errorText.collectAsState()
    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            forecast?.alerts?.let { alerts->
                AlertsBlock(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    alerts = alerts
                )
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
    error?.let { error->
        ErrorMessage(error)
    }

}
@Composable
private fun ErrorMessage(error:UIError) {
    var visible by remember { mutableStateOf<Boolean>(false) }
    var message by remember { mutableStateOf<String>("") }
    LaunchedEffect(key1 = error){
        message = error.message
        visible = true
        delay(3000)
        visible = false

    }
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.errorContainer,
            tonalElevation = 4.dp
        ) {
            message?.let { message->
                Text(
                    text = message,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
@Composable
fun AlertsBlock(
    modifier: Modifier = Modifier,
    alerts: Alerts
){
    if(alerts.alert.isNotEmpty()){
        Surface(
            modifier = modifier,
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.error
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                alerts.alert.forEach{alert->
                    alert.event?.let { Text(text = it) }
                }
            }
        }
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
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    forecast.current?.condition?.icon?.let {
                        GlideImage(
                            imageModel = { "https:" + it },
                            modifier = Modifier.size(64.dp)
                        )
                    }
                    forecast.current?.condition?.text?.let { Text(text = it, textAlign = TextAlign.Center) }

                }
            }
            forecast.current?.windKph?.let { windSpeed ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = stringResource(id = R.string.wind))
                    Text(text = speedInUnits(windSpeed,weatherConfig.speedUnit))
                }
            }
            forecast.current?.humidity?.let { humidity ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = stringResource(id = R.string.humidity))
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
                        Text(text = stringResource(id = R.string.updated))
                        Text(text = "${lastUpdated.subSequence(8, 10)}.${lastUpdated.subSequence(5, 7)} "+lastUpdated.split(" ")[1])
                    }
                    IconButton(onClick = { refreshOnClick() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "refresh",modifier = Modifier.size(64.dp))
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.temperature_icon),
                    contentDescription = "temperature",
                    modifier = Modifier.requiredWidth(14.dp)
                )
                Text(text = temepatureInUnits(it,weatherConfig.degreeUnit))
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
    var expanded by remember { mutableStateOf(false) }
    Card(
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.large
            )
            .clip(MaterialTheme.shapes.large)
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
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
            AnimatedVisibility(visible = expanded) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    hour.humidity?.let {
                        Icon(painter = painterResource(id = R.drawable.humidity_icon), contentDescription = "humidity",modifier = Modifier.size(44.dp))
                        Text(text = "$it%")
                    }
                    hour.windDir?.let {
                        Icon(painter = painterResource(id = R.drawable.wind_direction_icon), contentDescription = "wind direction",modifier = Modifier.size(44.dp))
                        Text(text = it)
                    }
                    hour.uv?.let {
                        Icon(painter = painterResource(id = R.drawable.uv_icon), contentDescription = "uv",modifier = Modifier.size(44.dp))
                        Text(text = it.toString())
                    }
                }
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
            day?.condition?.text?.let{
                Text(text =it, textAlign = TextAlign.Center,modifier=Modifier.fillMaxWidth())
            }
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
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Text(text = stringResource(id = R.string.max_wind))
                            Text(text = speedInUnits(it,weatherConfig.speedUnit))
                        }
                    }
                    day?.avghumidity?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Text(text = stringResource(id = R.string.humidity))
                            Text(text = "${it}")
                        }
                    }
                    day?.uv?.let {
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
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(6.dp)
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
                    Text(text = "${stringResource(id = R.string.illumination)}  ${it}%")
                }
            }
        }
    }
}

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