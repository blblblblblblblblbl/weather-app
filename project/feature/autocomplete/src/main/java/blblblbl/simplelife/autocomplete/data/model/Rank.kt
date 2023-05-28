package blblblbl.simplelife.autocomplete.data.model

import com.google.gson.annotations.SerializedName


data class Rank (

  @SerializedName("importance"            ) var importance          : Double? = null,
  @SerializedName("confidence"            ) var confidence          : Int?    = null,
  @SerializedName("confidence_city_level" ) var confidenceCityLevel : Int?    = null,
  @SerializedName("match_type"            ) var matchType           : String? = null

)