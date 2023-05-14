package blblblbl.simplelife.cities.domain.model


data class ForecastResponse (

    var location : Location? = Location(),
    var current  : Current?  = Current(),
    var forecast : Forecast? = Forecast()

)