@file:JvmName("MainScreenControllerKt")

package blblblbl.simplelife.main_screen.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import blblblbl.simplelife.coreutils.utils.compose.ErrorMessage
import blblblbl.simplelife.main_screen.presentation.MainScreenFragmentViewModel


@Composable
fun MainScreenController(
    menuOnCLick: () -> Unit
) {
    val viewModel = hiltViewModel<MainScreenFragmentViewModel>()
    val forecast by viewModel.forecast.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val appConfig by viewModel.settings.collectAsState()
    val loadState by viewModel.loadState.collectAsState()
    val isInFavourites by viewModel.isInFavourites.collectAsState()
    val error by viewModel.errorText.collectAsState()
    val cityVariants by viewModel.cityVariants.collectAsState()
    val context = LocalContext.current

    DisposableEffect(key1 = viewModel) {
        viewModel.checkInFavourites()
        onDispose {
            viewModel.errorClear()
        }
    }
    MainScreenView(
        forecast = forecast,
        appConfig = appConfig,
        loadState = loadState,
        isInFavourites = isInFavourites,
        searchQuery = searchQuery,
        onRefreshClick = { viewModel.refresh(context) },
        onFavouriteClick = {
            if (isInFavourites) {
                viewModel.removeFromFavourites()
            } else {
                viewModel.addToFavourites()
            }
        },
        onMenuClick = { menuOnCLick() },
        onTextChange = {
            viewModel.updateSearchQuery(query = it)
            viewModel.searchCompletions()
        },
        onSearchClick = {
            viewModel.getForecastByName(context)
        },
        onCityClick = {
            viewModel.updateSearchQuery(it)
            viewModel.getForecastByName(context)
        },
        onClearClick = {
            viewModel.updateSearchQuery("")
            viewModel.clearCompletions()
        },
        onLocationClick = { viewModel.locationOnClick(context) },
        suggestedVariants = cityVariants ?: listOf<String>()
    )
    error?.let { error ->
        ErrorMessage(error)
    }
}












