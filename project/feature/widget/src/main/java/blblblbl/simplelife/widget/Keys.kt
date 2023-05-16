package blblblbl.simplelife.widget


import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.action.ActionParameters

object WidgetKeys{
    object Prefs {
        val cityNamePK = stringPreferencesKey("cityNamePK")
        val forecastJSONPK = stringPreferencesKey("forecastJSONPK")
    }

}