package blblblbl.simplelife.forecast.utils

import android.content.Context
import android.content.res.AssetManager
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import com.google.gson.GsonBuilder

/**
 * @author Kirill Tolmachev 05.08.2023
 */

fun forecastResponseForPreview(context: Context): ForecastResponse {
    val gson = GsonBuilder().setLenient().create()
    val json = context.assets.readAssetsFile("forecastJson")
    return gson.fromJson(json, ForecastResponse::class.java)
}

fun AssetManager.readAssetsFile(fileName : String): String = open(fileName).bufferedReader().use{it.readText()}