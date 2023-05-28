package blblblbl.simplelife.autocomplete.data.model

import com.google.gson.annotations.SerializedName


data class Results (

  @SerializedName("datasource"    ) var datasource   : Datasource? = Datasource(),
  @SerializedName("old_name"      ) var oldName      : String?     = null,
  @SerializedName("country"       ) var country      : String?     = null,
  @SerializedName("country_code"  ) var countryCode  : String?     = null,
  @SerializedName("region"        ) var region       : String?     = null,
  @SerializedName("state"         ) var state        : String?     = null,
  @SerializedName("city"          ) var city         : String?     = null,
  @SerializedName("lon"           ) var lon          : Double?     = null,
  @SerializedName("lat"           ) var lat          : Double?     = null,
  @SerializedName("formatted"     ) var formatted    : String?     = null,
  @SerializedName("address_line1" ) var addressLine1 : String?     = null,
  @SerializedName("address_line2" ) var addressLine2 : String?     = null,
  @SerializedName("category"      ) var category     : String?     = null,
  @SerializedName("timezone"      ) var timezone     : Timezone?   = Timezone(),
  @SerializedName("result_type"   ) var resultType   : String?     = null,
  @SerializedName("rank"          ) var rank         : Rank?       = Rank(),
  @SerializedName("place_id"      ) var placeId      : String?     = null,
  @SerializedName("bbox"          ) var bbox         : Bbox?       = Bbox()

)