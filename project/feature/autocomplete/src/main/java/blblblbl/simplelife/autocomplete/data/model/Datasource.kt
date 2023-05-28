package blblblbl.simplelife.autocomplete.data.model

import com.google.gson.annotations.SerializedName


data class Datasource (

  @SerializedName("sourcename"  ) var sourcename  : String? = null,
  @SerializedName("attribution" ) var attribution : String? = null,
  @SerializedName("license"     ) var license     : String? = null,
  @SerializedName("url"         ) var url         : String? = null

)