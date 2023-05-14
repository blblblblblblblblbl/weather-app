package blblblbl.simplelife.cities.data.model


data class Hour (

    var timeEpoch    : Long?       = null,
    var time         : String?    = null,
    var tempC        : Double?    = null,
    var tempF        : Double?    = null,
    var isDay        : Int?       = null,
    var condition    : Condition? = Condition(),
    var windMph      : Double?    = null,
    var windKph      : Double?    = null,
    var windDegree   : Int?       = null,
    var windDir      : String?    = null,
    var pressureMb   : Double?       = null,
    var pressureIn   : Double?    = null,
    var precipMm     : Double?       = null,
    var precipIn     : Double?       = null,
    var humidity     : Int?       = null,
    var cloud        : Int?       = null,
    var feelslikeC   : Double?    = null,
    var feelslikeF   : Double?       = null,
    var windchillC   : Double?    = null,
    var windchillF   : Double?       = null,
    var heatindexC   : Double?    = null,
    var heatindexF   : Double?    = null,
    var dewpointC    : Double?       = null,
    var dewpointF    : Double?    = null,
    var willItRain   : Int?       = null,
    var chanceOfRain : Int?       = null,
    var willItSnow   : Int?       = null,
    var chanceOfSnow : Int?       = null,
    var visKm        : Double?       = null,
    var visMiles     : Double?       = null,
    var gustMph      : Double?    = null,
    var gustKph      : Double?    = null,
    var uv           : Double?       = null

)