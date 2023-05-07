package blblblbl.simplelife.settings.domain.repository

import blblblbl.simplelife.settings.domain.model.config.AppConfig
import kotlinx.coroutines.flow.StateFlow

interface AppConfigRepository {
    val config:StateFlow<AppConfig?>

    fun saveConfig(config:AppConfig)

    fun getConfig():AppConfig
}