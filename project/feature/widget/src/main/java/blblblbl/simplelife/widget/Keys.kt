package blblblbl.simplelife.widget


import androidx.datastore.preferences.core.stringPreferencesKey

object WidgetKeys{
    object Prefs {
        val cityNamePK = stringPreferencesKey("cityNamePK")
        val forecastJSONPK = stringPreferencesKey("forecastJSONPK")
        val weatherConfigPK = stringPreferencesKey("weatherConfigPK")
    }

}