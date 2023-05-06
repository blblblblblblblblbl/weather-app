package blblblbl.simplelife.settings.data.persistent_storage

import android.content.SharedPreferences
import android.util.Log
import blblblbl.simplelife.settings.data.model.config.AppConfig
import blblblbl.simplelife.settings.data.persistent_storage.utils.StorageConverter
import blblblbl.simplelife.settings.di.SettingsFeature
import javax.inject.Inject

class AppConfigPersistentStorageImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
):AppConfigPersistentStorage {
    @Inject
    @SettingsFeature
    lateinit var storageConverter: StorageConverter

    private val editor = sharedPreferences.edit()
    override fun addProperty(name: String?, value: String?) {

        Log.d("MyLog", "addProperty:$name $value")
        editor.putString(name, value)
        editor.apply()
    }

    override fun clear() {
        editor.clear()
        editor.apply()
    }

    override fun getProperty(name: String?): String? {
        return sharedPreferences.getString(name, null)
    }

    override fun addConfig(config: AppConfig) {
        val value = storageConverter.configToJson(config)
        editor.putString(AppConfigPersistentStorage.APP_CONFIG_KEY, value)
        editor.apply()
    }

    override fun getConfig(): AppConfig? {
        val json = sharedPreferences.getString(AppConfigPersistentStorage.APP_CONFIG_KEY, null)
        val config = storageConverter.configFromJson(json ?: "")
        return config
    }
}