package blblblbl.simplelife.main_screen.data.persistent_storage.utils




import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.main_screen.di.LastSearchFeature
import javax.inject.Inject

class StorageConverterImpl @Inject constructor() : StorageConverter
{
    @Inject
    @LastSearchFeature
    lateinit var jsonParser: JsonParser
    override fun forecastToJson(config: ForecastResponse): String? {
        return jsonParser.toJson(
            config,
            ForecastResponse::class.java
        )
    }

    override fun forecastFromJson(json: String): ForecastResponse? {
        return jsonParser.fromJson<ForecastResponse>(
            json,
            ForecastResponse::class.java
        )
    }
}