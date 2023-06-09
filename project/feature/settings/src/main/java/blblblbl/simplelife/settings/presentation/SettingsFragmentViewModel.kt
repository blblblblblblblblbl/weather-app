package blblblbl.simplelife.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blblblbl.simplelife.settings.domain.model.config.AppConfig
import blblblbl.simplelife.settings.domain.usecase.GetAppConfigUseCase
import blblblbl.simplelife.settings.domain.usecase.SaveAppConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(
    private val getAppConfigUseCase: GetAppConfigUseCase,
    private val saveAppConfigUseCase: SaveAppConfigUseCase
):ViewModel() {
    private val _config = MutableStateFlow<AppConfig?>(null)
    val config = _config.asStateFlow()
    fun getConfig(){
        viewModelScope.launch {
            val config = getAppConfigUseCase.getAppConfig()
            _config.value = config
        }
    }

    fun saveConfig(config:AppConfig){
        viewModelScope.launch {
            saveAppConfigUseCase.execute(config)
            _config.value = config
        }
    }
}