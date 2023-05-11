package blblblbl.simplelife.main_screen.data.persistent_storage

import android.content.SharedPreferences
import android.util.Log
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.main_screen.data.persistent_storage.utils.StorageConverter
import blblblbl.simplelife.main_screen.di.LastSearchFeature
import javax.inject.Inject

class LastSearchPersistentStorageImpl @Inject constructor(
    @LastSearchFeature private val sharedPreferences: SharedPreferences
):LastSearchPersistentStorage {
    @Inject
    @LastSearchFeature
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

    override fun addLast(last: ForecastResponse) {
        val value = storageConverter.forecastToJson(last)
        editor.putString(LastSearchPersistentStorage.LAST_SEARCH_KEY, value)
        editor.apply()
    }

    override fun getLast(): ForecastResponse? {
        val json = sharedPreferences.getString(LastSearchPersistentStorage.LAST_SEARCH_KEY, null)
        val last = storageConverter.forecastFromJson(json ?: "")
        return last
    }
}