package blblblbl.simplelife.forecast.data.model

import com.google.gson.annotations.SerializedName


data class ForecastResponse (

    @SerializedName("location" ) var location : Location? = Location(),
    @SerializedName("current"  ) var current  : Current?  = Current(),
    @SerializedName("forecast" ) var forecast : Forecast? = Forecast()

)