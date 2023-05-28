package blblblbl.simplelife.autocomplete.data.model

import com.google.gson.annotations.SerializedName


data class Query (

  @SerializedName("text"   ) var text   : String? = null,
  @SerializedName("parsed" ) var parsed : Parsed? = Parsed()

)