package blblblbl.simplelife.weather.di.api.forecast

import android.util.Log
import blblblbl.simplelife.forecast.data.utils.mapToData
import blblblbl.simplelife.forecast.data.utils.mapToDomain
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse as DomainForecastResponse
import blblblbl.simplelife.forecast.data.model.forecast.ForecastResponse as DataForecastResponse
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class ForecastResponseAdapter: TypeAdapter<DomainForecastResponse>() {
    override fun write(out: JsonWriter?, value: DomainForecastResponse?) {
    }

    override fun read(`in`: JsonReader?): DomainForecastResponse {
        val gson = GsonBuilder().setLenient().create()
        return gson.fromJson<DataForecastResponse>(`in`,DataForecastResponse::class.java).mapToDomain()
    }
}