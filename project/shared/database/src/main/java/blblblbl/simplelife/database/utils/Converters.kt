package blblblbl.simplelife.database.utils

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import blblblbl.simplelife.database.model.ForecastResponse

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun toMeaningJson(forecastResponse: ForecastResponse) : String {
        return jsonParser.toJson(
            forecastResponse,
            ForecastResponse::class.java
        ) ?: "[]"
    }

    @TypeConverter
    fun fromMeaningsJson(json: String): ForecastResponse {
        return jsonParser.fromJson<ForecastResponse>(
            json,
            ForecastResponse::class.java
        ) ?: ForecastResponse()
    }
}