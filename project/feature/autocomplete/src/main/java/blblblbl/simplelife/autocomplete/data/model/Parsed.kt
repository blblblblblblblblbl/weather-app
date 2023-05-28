package blblblbl.simplelife.autocomplete.data.model

import com.google.gson.annotations.SerializedName


data class Parsed (

  @SerializedName("city"          ) var city         : String? = null,
  @SerializedName("expected_type" ) var expectedType : String? = null

)