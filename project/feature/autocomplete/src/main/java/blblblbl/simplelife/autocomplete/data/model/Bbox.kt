package blblblbl.simplelife.autocomplete.data.model

import com.google.gson.annotations.SerializedName


data class Bbox (

  @SerializedName("lon1" ) var lon1 : Double? = null,
  @SerializedName("lat1" ) var lat1 : Double? = null,
  @SerializedName("lon2" ) var lon2 : Double? = null,
  @SerializedName("lat2" ) var lat2 : Double? = null

)