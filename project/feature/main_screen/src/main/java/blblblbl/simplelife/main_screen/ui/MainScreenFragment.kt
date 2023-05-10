package blblblbl.simplelife.main_screen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import blblblbl.simplelife.main_screen.presentation.MainScreenFragmentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenFragment(){
    val viewModel = hiltViewModel<MainScreenFragmentViewModel>()
    val forecast by viewModel.forecast.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val context = LocalContext.current
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            Box(modifier = Modifier.padding(10.dp)){
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
    ){
        Surface(modifier = Modifier.padding(it)) {
            Text(forecast.toString())
        }
    }
}