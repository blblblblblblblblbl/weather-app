package blblblbl.simplelife.main_screen.ui.component

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import blblblbl.simplelife.forecast.domain.model.forecast.Astro
import blblblbl.simplelife.main_screen.R

/**
 * @author Kirill Tolmachev 04.08.2023
 */

@Composable
fun AstroView(
    astro: Astro,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(6.dp)
        ) {
            Column() {
                val sunrise = astro.sunrise
                val sunset = astro.sunset
                if (sunrise != null && sunset != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        IconWithText(
                            text = sunrise,
                            iconResId = R.drawable.sunrise,
                            contentDescription = "sunrise"
                        )
                        IconWithText(
                            text = sunset,
                            iconResId = R.drawable.sunset,
                            contentDescription = "sunset"
                        )
                    }
                }
                val moonrise = astro.moonrise
                val moonset = astro.moonset
                if (moonrise != null && moonset != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        IconWithText(
                            text = moonrise,
                            iconResId = R.drawable.moonrise,
                            contentDescription = "moonrise"
                        )
                        IconWithText(
                            text = moonset,
                            iconResId = R.drawable.moonset,
                            contentDescription = "moonset"
                        )
                    }
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                astro.moonPhase?.let { moonPhase ->
                    Text(text = moonPhase)
                    Icon(
                        painter = painterResource(id = resolveMoonPhaseIcon(moonPhase)),
                        contentDescription = moonPhase,
                        modifier = Modifier.size(64.dp)
                    )
                }
                astro.moonIllumination?.let {
                    Text(text = "${stringResource(id = R.string.illumination)}  ${it}%")
                }
            }
        }
    }
}

private fun resolveMoonPhaseIcon(moonPhase: String) = when (moonPhase) {
    "New Moon" -> R.drawable.new_moon
    "Waxing Crescent" -> R.drawable.waning_crescent
    "First Quarter" -> R.drawable.first_quarter
    "Waxing Gibbous" -> R.drawable.waxing_gibbous
    "Full Moon" -> R.drawable.full_moon
    "Waning Gibbous" -> R.drawable.waning_gibbous
    "Last Quarter" -> R.drawable.last_quarter
    "Waning Crescent" -> R.drawable.waning_crescent
    else -> R.drawable.waxing_gibbous
}

@Composable
private fun IconWithText(
    text: String,
    @DrawableRes iconResId: Int,
    contentDescription: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painterResource(id = iconResId),
            contentDescription = contentDescription,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    AstroView(
        Astro(
            sunrise = "10:00 AM",
            sunset = "10:00 AM",
            moonrise = "10:00 AM",
            moonset = "10:00 AM",
            moonIllumination = 78.0,
            moonPhase = "Full Moon"
        )
    )
}

