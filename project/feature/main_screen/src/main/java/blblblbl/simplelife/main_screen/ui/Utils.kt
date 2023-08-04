package blblblbl.simplelife.main_screen.ui


import blblblbl.simplelife.settings.domain.model.config.weather.DegreeUnit
import blblblbl.simplelife.settings.domain.model.config.weather.SpeedUnit

class UIError(val message: String)

fun temperatureInUnits(tempC:Double, unit: DegreeUnit):String{
    return when (unit){
        DegreeUnit.C->{
            "${tempC}°C"
        }
        DegreeUnit.F->{
            "${(tempC*1.8+32).format(1)}°F"
        }
    }
}
fun speedInUnits(speed:Double,unit: SpeedUnit):String{
    return when (unit){
        SpeedUnit.Kmh->{
            "${speed} km/h"
        }
        SpeedUnit.Mph->{
            "${(speed/1.609).format(1)} mil/h"
        }
        SpeedUnit.Ms->{
            "${(speed/3.6).format(1)} m/s"
        }
    }
}
fun Double.format(digits: Int) = "%.${digits}f".format(this)