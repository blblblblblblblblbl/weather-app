package blblblbl.simplelife.settings.data.persistent_storage.utils




import blblblbl.simplelife.settings.data.model.config.AppConfig
import blblblbl.simplelife.settings.di.SettingsFeature
import javax.inject.Inject

class StorageConverterImpl @Inject constructor() : StorageConverter
{
    @Inject
    @SettingsFeature
    lateinit var jsonParser: JsonParser
    override fun configToJson(config: AppConfig): String? {
        return jsonParser.toJson(
            config,
            AppConfig::class.java
        )
    }

    override fun configFromJson(json: String): AppConfig? {
        return jsonParser.fromJson<AppConfig>(
            json,
            AppConfig::class.java
        )
    }
}