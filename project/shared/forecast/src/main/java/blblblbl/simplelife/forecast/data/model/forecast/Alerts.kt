package blblblbl.simplelife.forecast.data.model.forecast

import com.google.gson.annotations.SerializedName


data class Alerts (
    @SerializedName("alert" ) var alert : ArrayList<Alert> = arrayListOf()
)