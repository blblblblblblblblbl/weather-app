package blblblbl.simplelife.autocomplete.data.model

import com.google.gson.annotations.SerializedName


data class Response (

  @SerializedName("results" ) var results : ArrayList<Results> = arrayListOf(),
  @SerializedName("query"   ) var query   : Query?             = Query()

)