package blblblbl.simplelife.settings.data.persistent_storage.utils


import blblblbl.simplelife.settings.data.model.config.AppConfig


interface StorageConverter {

    fun configToJson(config: AppConfig): String?

    fun configFromJson(json: String): AppConfig?
}