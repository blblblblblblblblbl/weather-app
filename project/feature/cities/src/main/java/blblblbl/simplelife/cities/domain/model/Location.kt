package blblblbl.simplelife.cities.domain.model


data class Location (

  var name           : String? = null,
  var region         : String? = null,
  var country        : String? = null,
  var lat            : Double? = null,
  var lon            : Double? = null,
  var tzId           : String? = null,
  var localtimeEpoch : Long?    = null,
  var localtime      : String? = null

)