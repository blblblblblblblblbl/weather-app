package blblblbl.simplelife.forecast.domain.model.forecast

import com.google.gson.annotations.SerializedName


data class Alerts (
    @SerializedName("alert" ) var alert : List<Alert> = listOf()
)