package blblblbl.simplelife.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
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
import blblblbl.simplelife.main_screen.ui.MainScreenFragment
import blblblbl.simplelife.onboarding.OnBoardingScreen
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enqueueWidgetsUpdate()
        lifecycleScope.launch {
            viewModel.getSettingsFlow().collect{appConfig->
                appConfig?.weatherConfig?.let {
                    viewModel.updateWidgetWeatherConfig(it)
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

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppScreen(
                        startDestination = MainDest,
                        showOnBoarding = false,//(this::showOnBoarding.isInitialized)&&!showOnBoarding.IsShown(),
                        onBoardingOnClick= {/*showOnBoarding.saveShown()*/}
                    )
                }
            }
        }
    }
    fun enqueueWidgetsUpdate(){
        val updateWorkRequest: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<WidgetUpdateWorker>(60, repeatIntervalTimeUnit = TimeUnit.MINUTES)
                .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            UPDATE_WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            updateWorkRequest
        )
    }
    companion object{
        const val UPDATE_WORKER_NAME = "UPDATE_WIDGET_FORECAST_WORKER_NAME"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(
    startDestination: AppDestination = MainDest,
    showOnBoarding: Boolean = false,
    onBoardingOnClick:()->Unit = {}
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
                openMenu = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                },
                showOnBoarding = showOnBoarding,
                onBoardingOnClick = onBoardingOnClick
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
    openMenu: ()->Unit,
    showOnBoarding: Boolean = false,
    onBoardingOnClick:()->Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {
        composable(route = MainDest.route) {
            MainScreenFragment()
        }
        citiesGraph(navController)
        composable(route = AppSettingDest.route) {
            SettingsFragment()
        }
        composable(route = OnBoardingDest.route) {
            OnBoardingScreen {
                navController.navigate(MainDest.route)
            }
        }
        composable(route = AuthorsDest.route) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Authors", style = MaterialTheme.typography.headlineLarge)
                    Text(text = "Kirill Tolmachev", style = MaterialTheme.typography.headlineLarge)
                    Text(text = "Varvara Sapozhnikova", style = MaterialTheme.typography.headlineLarge)
                }
            }
        }
    }
}
