package blblblbl.simplelife.main_screen.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.main_screen.presentation.LoadingState
import blblblbl.simplelife.main_screen.ui.component.AlertsBlock
import blblblbl.simplelife.main_screen.ui.component.CurrentWeatherBlock
import blblblbl.simplelife.main_screen.ui.component.NextDays
import blblblbl.simplelife.main_screen.ui.component.SearchWidget
import blblblbl.simplelife.settings.domain.model.config.AppConfig

/**
 * @author Kirill Tolmachev 04.08.2023
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenView(
    forecast: ForecastResponse?,
    appConfig: AppConfig?,
    loadState: LoadingState,
    isInFavourites: Boolean,
    searchQuery: String,
    onRefreshClick: () -> Unit,
    onFavouriteClick: () -> Unit,
    onMenuClick: () -> Unit,
    onTextChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onCityClick: (String) -> Unit,
    onClearClick: () -> Unit,
    onLocationClick: () -> Unit,
    suggestedVariants: List<String>

) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = { FakeTopBar(Modifier.padding(10.dp).alpha(0f)) }
    ) {
        Surface(modifier = Modifier.fillMaxSize().padding(it)) {
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
                            refreshOnClick = onRefreshClick,
                            weatherConfig = weatherConfig
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(onClick = onFavouriteClick) {
                                AnimatedContent(targetState = isInFavourites) {
                                    if (isInFavourites){
                                        Icon(Icons.Default.Favorite, contentDescription = "favourites button")
                                    }
                                    else{
                                        Icon(Icons.Default.FavoriteBorder, contentDescription = "favourites button")
                                    }
                                }
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
    Box(modifier = Modifier.padding(10.dp)) {
        Row(verticalAlignment = Alignment.Top) {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "menu button",modifier = Modifier.size(48.dp))
            }
            SearchWidget(
                text = searchQuery,
                onTextChange = onTextChange,
                onSearchClick = onSearchClick,
                onClearClick = onClearClick,
                onLocationClick = onLocationClick,
                suggestedVariants = suggestedVariants,
                onCityClick = { onCityClick(it) }
            )
        }
    }
}

@Composable
private fun FakeTopBar(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Row(verticalAlignment = Alignment.Top) {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Menu, contentDescription = "menu button",modifier = Modifier.size(48.dp))
            }
            SearchWidget(text = "", onTextChange = {}, onSearchClick = {}, onClearClick = {}, onLocationClick = {}, suggestedVariants = listOf<String>(), onCityClick = {})
        }
    }
}