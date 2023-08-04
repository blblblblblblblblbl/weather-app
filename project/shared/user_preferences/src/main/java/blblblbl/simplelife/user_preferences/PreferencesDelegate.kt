package blblblbl.simplelife.user_preferences

import android.content.SharedPreferences
import kotlin.reflect.KProperty

/**
 * @author Kirill Tolmachev 03.08.2023
 */

fun SharedPreferences.booleanDelegate(key: String, default: Boolean) =
    PreferencesBooleanDelegate(this, key, default)

class PreferencesBooleanDelegate(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val default: Boolean
) {
    private var cache: Boolean? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        val cache = cache
        if (cache != null) {
            return cache
        }
        return sharedPreferences.getBoolean(key, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        cache = value
        sharedPreferences.edit().putBoolean(key, value).apply()
    }
}