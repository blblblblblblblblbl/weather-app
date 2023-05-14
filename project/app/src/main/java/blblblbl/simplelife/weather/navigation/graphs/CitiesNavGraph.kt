package blblblbl.simplelife.weather.navigation.graphs

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import blblblbl.simplelife.cities.ui.CitiesFragment
import blblblbl.simplelife.weather.navigation.CitiesDest
import java.time.LocalDate

fun NavGraphBuilder.citiesGraph(navController: NavController){
    navigation(startDestination = CitiesDest.route, route = "CitiesNested") {
        composable(route = CitiesDest.route) {
            /*Surface(modifier = Modifier.fillMaxSize()) {
                Box() {
                    val city = "city"
                    Button(
                        modifier =  Modifier.align(Alignment.Center),
                        onClick = { navController.navigate("${CitiesNavGraph.DETAILED_CITY_ROUTE}/${"$city"}")}) {
                        Text(text = city)
                    }
                }

            }*/
            CitiesFragment()
        }
        composable(
            route =  "${CitiesNavGraph.DETAILED_CITY_ROUTE}/{${CitiesNavGraph.DETAILED_CITY_KEY}}",
            arguments = listOf(
                navArgument(name = CitiesNavGraph.DETAILED_CITY_KEY){
                    type = NavType.StringType
                }
            )
        ){navBackStackEntry ->
            val dateString = navBackStackEntry.arguments?.getString(CitiesNavGraph.DETAILED_CITY_KEY)
            dateString?.let {cityName->
                Text(text = cityName)
            }
        }
    }
}
private object CitiesNavGraph{
    const val DETAILED_CITY_ROUTE = "CitiesNavGraph.DetailedRoute"
    const val DETAILED_CITY_KEY = "CitiesNavGraph.DetailedCityKey"
}