package blblblbl.simplelife.weather.presentation

import androidx.lifecycle.ViewModel
import blblblbl.simplelife.settings.domain.model.config.AppConfig
import blblblbl.simplelife.settings.domain.repository.AppConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val settingsRepository: AppConfigRepository
):ViewModel(){
    fun getSettingsFlow():StateFlow<AppConfig?> = settingsRepository.config

}