package blblblbl.simplelife.cities.data.model


data class ForecastResponse (

    var location : Location? = Location(),
    var current  : Current?  = Current(),
    var forecast : Forecast? = Forecast()

)