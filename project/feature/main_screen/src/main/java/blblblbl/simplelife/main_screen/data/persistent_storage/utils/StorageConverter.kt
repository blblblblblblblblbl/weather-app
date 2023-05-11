package blblblbl.simplelife.main_screen.data.persistent_storage.utils


import blblblbl.simplelife.forecast.domain.model.ForecastResponse


interface StorageConverter {

    fun forecastToJson(config: ForecastResponse): String?

    fun forecastFromJson(json: String): ForecastResponse?
}