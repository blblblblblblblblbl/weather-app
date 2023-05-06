package blblblbl.simplelife.settings.data.persistent_storage

import blblblbl.simplelife.settings.data.model.config.AppConfig

interface AppConfigPersistentStorage {
    fun addProperty(key: String?, value: String?)

    fun clear()

    fun getProperty(key: String?): String?

    fun addConfig(config: AppConfig)

    fun getConfig(): AppConfig?

    companion object {
        const val APP_CONFIG_KEY = "app_config_key"
    }
}