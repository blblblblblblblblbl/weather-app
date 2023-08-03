package blblblbl.simplelife.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import blblblbl.simplelife.onboarding.OnBoardingScreenView
import blblblbl.simplelife.settings.ui.SettingsFragment
import blblblbl.simplelife.weather.navigation.AppDestination
import blblblbl.simplelife.weather.navigation.AppSettingDest
import blblblbl.simplelife.weather.navigation.AuthorsDest
import blblblbl.simplelife.weather.navigation.DrawerContent
import blblblbl.simplelife.weather.navigation.MainDest
import blblblbl.simplelife.weather.navigation.OnBoardingDest
import blblblbl.simplelife.weather.navigation.graphs.citiesGraph
import blblblbl.simplelife.weather.presentation.MainActivityViewModel
import blblblbl.simplelife.weather.ui.WidgetUpdateWorker
import blblblbl.simplelife.weather.ui.theme.WeatherTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.getSettingsFlow().collect{appConfig->
                appConfig?.weatherConfig?.let {
                    viewModel.updateWidgetWeatherConfig(it)
                }
                appConfig?.widgetConfig?.let {widgetConfig->
                    val updateWorkRequest: PeriodicWorkRequest =
                        PeriodicWorkRequestBuilder<WidgetUpdateWorker>(widgetConfig.updateTime, repeatIntervalTimeUnit = TimeUnit.MINUTES)
                            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                            .build()
                    WorkManager.getInstance(this@MainActivity).enqueueUniquePeriodicWork(
                        UPDATE_WORKER_NAME,
                        ExistingPeriodicWorkPolicy.UPDATE,
                        updateWorkRequest
                    )
                }
            }
        }
        setContent {
            val systemUiController = rememberSystemUiController()
            WeatherTheme(
                configFlow = viewModel.getSettingsFlow()
            ) {
                val color = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp)
                SideEffect {
                    systemUiController.setSystemBarsColor(color)
                }
                Surface {
                    AppScreen()
                }
            }
        }
    }
    companion object{
        const val UPDATE_WORKER_NAME = "UPDATE_WIDGET_FORECAST_WORKER_NAME"
    }
}

@Composable
fun AppScreen(
    startDestination: AppDestination = MainDest
){
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    Surface() {
        ModalNavigationDrawer(
            drawerContent =
            {
                ModalDrawerSheet(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 50.dp)
                ) {
                    DrawerContent(
                        navController = navController,
                        drawerState = drawerState
                    )
                }

            },
            gesturesEnabled = true,
            drawerState = drawerState
        ) {
            AppNavHost(
                navController = navController,
                startDestination = startDestination,
                menuOnclick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }
            )
        }
    }

}
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination:AppDestination = MainDest,
    menuOnclick:()->Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {
        composable(route = MainDest.route) {
            //MainScreen(menuOnclick)
        }
        citiesGraph(navController)
        composable(route = AppSettingDest.route) {
            SettingsFragment()
        }
        composable(route = OnBoardingDest.route) {
            OnBoardingScreenView {
                navController.navigate(MainDest.route)
            }
        }
        composable(route = AuthorsDest.route) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    val context = LocalContext.current
                    Text(text = context.resources.getString(R.string.authors), style = MaterialTheme.typography.headlineLarge)
                    Text(text = "Kirill Tolmachev", style = MaterialTheme.typography.headlineLarge)
                    Text(text = "Varvara Sapozhnikova", style = MaterialTheme.typography.headlineLarge)
                }
            }
        }
    }
}
