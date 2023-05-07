package blblblbl.simplelife.settings.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.settings.domain.model.config.weather.DegreeUnit
import blblblbl.simplelife.settings.domain.model.config.weather.SpeedUnit
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig

@Composable
fun WeatherConfigPicker(
    modifier: Modifier = Modifier,
    initial: WeatherConfig,
    setConfig: (WeatherConfig) -> Unit
) {
    DropDownCard(
        modifier = modifier,
        header = "weather",
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "temperature")
                Spacer(modifier = Modifier.weight(1f))
                DegreeUnitPicker(
                    initial = initial.degreeUnit,
                    selectUnit = {degreeUnit->
                        setConfig(initial.copy(degreeUnit = degreeUnit))
                    }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "wind speed")
                Spacer(modifier = Modifier.weight(1f))
                SpeedUnitPicker(
                    initial = initial.speedUnit,
                    selectUnit = {speedUnit->
                        setConfig(initial.copy(speedUnit = speedUnit))
                    }
                )
            }
        }
    }
}

@Composable
fun DegreeUnitPicker(
    initial: DegreeUnit,
    selectUnit: (DegreeUnit) -> Unit
) {
    DropDownPicker<DegreeUnit>(
        variants = DegreeUnit.values(),
        initial = initial,
        onSelected = selectUnit
    )
}

@Composable
fun SpeedUnitPicker(
    initial: SpeedUnit,
    selectUnit: (SpeedUnit) -> Unit
) {
    DropDownPicker<SpeedUnit>(
        variants = SpeedUnit.values(),
        initial = initial,
        onSelected = selectUnit
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropDownPicker(
    variants: Array<T>,
    initial: T,
    onSelected: (T) -> Unit
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedVariant by remember { mutableStateOf(initial) }

    Box(
        modifier = Modifier
            //.fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedVariant.toString(),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                variants.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.toString()) },
                        onClick = {
                            selectedVariant = item
                            expanded = false
                            onSelected(item)
                        }
                    )
                }
            }
        }
    }
}

