package blblblbl.simplelife.cities.data.model


data class Forecastday (

    var date      : String?         = null,
    var dateEpoch : Long?            = null,
    var day       : Day?            = Day(),
    var astro     : Astro?          = Astro(),
    var hour      : ArrayList<Hour> = arrayListOf()

)