package blblblbl.simplelife.main_screen.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import blblblbl.simplelife.settings.domain.model.config.weather.DegreeUnit
import blblblbl.simplelife.settings.domain.model.config.weather.SpeedUnit

class UIError(val message: String)


@Composable
fun DropDownCard(
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Card(modifier = modifier) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded  },
                verticalAlignment = Alignment.CenterVertically
            ) {
                header()
            }
            AnimatedVisibility(expanded) {
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.outline
                )
            }
            AnimatedVisibility(expanded) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    content()
                }
            }
        }
    }
}
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