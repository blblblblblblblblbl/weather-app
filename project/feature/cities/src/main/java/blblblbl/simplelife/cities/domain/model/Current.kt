package blblblbl.simplelife.cities.domain.model



data class Current (

    var lastUpdatedEpoch : Long?       = null,
    var lastUpdated      : String?    = null,
    var tempC            : Double?       = null,
    var tempF            : Double?    = null,
    var isDay            : Int?       = null,
    var condition        : Condition? = Condition(),
    var windMph          : Double?    = null,
    var windKph          : Double?    = null,
    var windDegree       : Int?       = null,
    var windDir          : String?    = null,
    var pressureMb       : Double?       = null,
    var pressureIn       : Double?       = null,
    var precipMm         : Double?       = null,
    var precipIn         : Double?       = null,
    var humidity         : Int?       = null,
    var cloud            : Int?       = null,
    var feelslikeC       : Double?    = null,
    var feelslikeF       : Double?    = null,
    var visKm            : Double?       = null,
    var visMiles         : Double?       = null,
    var uv               : Double?       = null,
    var gustMph          : Double?    = null,
    var gustKph          : Double?    = null

)