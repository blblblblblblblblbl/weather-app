package blblblbl.simplelife.weather.navigation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import blblblbl.simplelife.weather.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(
    navController: NavHostController,
    drawerState: DrawerState
){
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(text = stringResource(id = R.string.app_name), textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(20.dp))
        NavigationDrawerItems(
            navController = navController,
            drawerState = drawerState
        )
        Divider()
        Spacer(modifier = Modifier.weight(1F))

        Button(onClick = {
            try {
                ContextCompat.startActivity(
                    context,
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + context.packageName)
                    ),
                    null
                )
            } catch (e: ActivityNotFoundException) {
                ContextCompat.startActivity(
                    context,
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + context.packageName)
                    ),
                    null
                )
            }
        }) {
            Text(text = stringResource(id = R.string.rate_app))
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerItems(
    navController: NavHostController,
    drawerState: DrawerState
){
    var scope = rememberCoroutineScope()
    var currentBackStackEntryAsState = navController.currentBackStackEntryAsState()
    var destination = currentBackStackEntryAsState.value?.destination
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxWidth()) {
        menuDestinations.forEach {dest->
            NavigationDrawerItem(
                label = { Text(text = stringResource(context.resources.getIdentifier(dest.name,"string",context.packageName)) ) },
                selected = destination?.route ==dest.route,
                onClick = {
                    navController.navigateSingleTopTo(dest.route)
                    scope.launch {
                        drawerState.close()
                    }
                })
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}