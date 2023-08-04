package blblblbl.simplelife.main_screen.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import blblblbl.simplelife.forecast.domain.model.forecast.Astro
import blblblbl.simplelife.main_screen.R

/**
 * @author Kirill Tolmachev 04.08.2023
 */

@Composable
fun AstroBlock(
    modifier: Modifier = Modifier,
    astro: Astro
){
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(6.dp)
        ) {
            Column() {
                val sunrise = astro.sunrise
                val sunset = astro.sunset
                if (sunrise!=null &&sunset!=null){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val iconSize = 48.dp
                        val textSize = 12.sp
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(painterResource(id = R.drawable.sunrise), contentDescription = "sunrise", modifier = Modifier.size(iconSize))
                            Text(text = sunrise, style = MaterialTheme.typography.bodySmall.copy(fontSize = textSize) )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(painterResource(id = R.drawable.sunset), contentDescription = "sunset", modifier = Modifier.size(iconSize))
                            Text(text = sunset, style = MaterialTheme.typography.bodySmall.copy(fontSize = textSize))
                        }
                    }
                }
                val moonrise = astro.moonrise
                val moonset = astro.moonset
                if (moonrise!=null &&moonset!=null){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val iconSize = 48.dp
                        val textSize = 12.sp
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(painterResource(id = R.drawable.moonrise), contentDescription = "moonrise", modifier = Modifier.size(iconSize))
                            Text(text = moonrise, style = MaterialTheme.typography.bodySmall.copy(fontSize = textSize))
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(painterResource(id = R.drawable.moonset), contentDescription = "moonset", modifier = Modifier.size(iconSize))
                            Text(text = moonset, style = MaterialTheme.typography.bodySmall.copy(fontSize = textSize))
                        }
                    }
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                astro.moonPhase?.let { moonPhase->
                    Text(text = moonPhase)
                    val iconSize = 64.dp
                    when(moonPhase){
                        "New Moon"-> Icon(painterResource(id = R.drawable.new_moon), contentDescription = "New Moon", modifier = Modifier.size(iconSize))
                        "Waxing Crescent"-> Icon(painterResource(id = R.drawable.waning_crescent), contentDescription = "Waxing Crescent", modifier = Modifier.size(iconSize))
                        "First Quarter"-> Icon(painterResource(id = R.drawable.first_quarter), contentDescription = "First Quarter", modifier = Modifier.size(iconSize))
                        "Waxing Gibbous"-> Icon(painterResource(id = R.drawable.waxing_gibbous), contentDescription = "Waxing Gibbous", modifier = Modifier.size(iconSize))
                        "Full Moon"-> Icon(painterResource(id = R.drawable.full_moon), contentDescription = "Full Moon", modifier = Modifier.size(iconSize))
                        "Waning Gibbous"-> Icon(painterResource(id = R.drawable.waning_gibbous), contentDescription = "Waning Gibbous", modifier = Modifier.size(iconSize))
                        "Last Quarter"-> Icon(painterResource(id = R.drawable.last_quarter), contentDescription = "Last Quarter", modifier = Modifier.size(iconSize))
                        "Waning Crescent"-> Icon(painterResource(id = R.drawable.waning_crescent), contentDescription = "Waning Crescent", modifier = Modifier.size(iconSize))
                    }
                }
                astro.moonIllumination?.let {
                    Text(text = "${stringResource(id = R.string.illumination)}  ${it}%")
                }
            }
        }
    }
}