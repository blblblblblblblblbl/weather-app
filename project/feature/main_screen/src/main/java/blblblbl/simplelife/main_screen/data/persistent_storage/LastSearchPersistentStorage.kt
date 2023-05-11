package blblblbl.simplelife.main_screen.data.persistent_storage

import blblblbl.simplelife.forecast.domain.model.ForecastResponse

interface LastSearchPersistentStorage {
    fun addProperty(key: String?, value: String?)

    fun clear()

    fun getProperty(key: String?): String?

    fun addLast(forecast: ForecastResponse)

    fun getLast(): ForecastResponse?

    companion object {
        const val LAST_SEARCH_KEY = "last_search_key"
    }
}