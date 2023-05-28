package blblblbl.simplelife.autocomplete.data.model

import com.google.gson.annotations.SerializedName


data class Timezone (

  @SerializedName("name"               ) var name             : String? = null,
  @SerializedName("offset_STD"         ) var offsetSTD        : String? = null,
  @SerializedName("offset_STD_seconds" ) var offsetSTDSeconds : Int?    = null,
  @SerializedName("offset_DST"         ) var offsetDST        : String? = null,
  @SerializedName("offset_DST_seconds" ) var offsetDSTSeconds : Int?    = null

)