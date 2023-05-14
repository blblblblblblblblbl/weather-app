package blblblbl.simplelife.cities.domain.model


data class Day (

    var maxtempC          : Double?       = null,
    var maxtempF          : Double?    = null,
    var mintempC          : Double?    = null,
    var mintempF          : Double?    = null,
    var avgtempC          : Double?       = null,
    var avgtempF          : Double?       = null,
    var maxwindMph        : Double?    = null,
    var maxwindKph        : Double?    = null,
    var totalprecipMm     : Double?       = null,
    var totalprecipIn     : Double?       = null,
    var totalsnowCm       : Double?       = null,
    var avgvisKm          : Double?    = null,
    var avgvisMiles       : Double?       = null,
    var avghumidity       : Int?       = null,
    var dailyWillItRain   : Int?       = null,
    var dailyChanceOfRain : Int?       = null,
    var dailyWillItSnow   : Int?       = null,
    var dailyChanceOfSnow : Int?       = null,
    var condition         : Condition? = Condition(),
    var uv                : Double?       = null

)