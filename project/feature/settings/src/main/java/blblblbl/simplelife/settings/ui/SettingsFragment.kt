package blblblbl.simplelife.settings.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import blblblbl.simplelife.settings.presentation.SettingsFragmentViewModel

@Composable
fun SettingsFragment(){
    val viewModel:SettingsFragmentViewModel = hiltViewModel()
    viewModel.getConfig()
    val config by viewModel.config.collectAsState()
    SettingsScreen(
        config = config,
        saveConfig = {conf->
            viewModel.saveConfig(conf)
        }
    )
}