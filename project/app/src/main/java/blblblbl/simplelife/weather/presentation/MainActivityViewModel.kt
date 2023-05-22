package blblblbl.simplelife.weather.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blblblbl.simplelife.settings.domain.model.config.AppConfig
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig
import blblblbl.simplelife.settings.domain.repository.AppConfigRepository
import blblblbl.simplelife.widget.di.update.UpdateWeatherWidget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val settingsRepository: AppConfigRepository,
    private val updateWeatherWidget: UpdateWeatherWidget
):ViewModel(){
    fun getSettingsFlow():StateFlow<AppConfig?> = settingsRepository.config

    fun updateWidgetWeatherConfig(weatherConfig: WeatherConfig) {
        viewModelScope.launch() {
            updateWeatherWidget.updateWeatherConfig(weatherConfig)
        }
    }
}