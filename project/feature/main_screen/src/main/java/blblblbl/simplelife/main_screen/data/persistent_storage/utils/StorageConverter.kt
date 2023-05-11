package blblblbl.simplelife.main_screen.data.persistent_storage.utils


import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse


interface StorageConverter {

    fun forecastToJson(config: ForecastResponse): String?

    fun forecastFromJson(json: String): ForecastResponse?
}