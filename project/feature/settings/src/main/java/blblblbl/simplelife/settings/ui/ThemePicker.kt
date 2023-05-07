package blblblbl.simplelife.settings.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.ModeNight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.settings.domain.model.config.theme.ThemeMode

@Composable
fun ThemePicker(
    saveColor: (Int) -> Unit,
    setMode: (ThemeMode) -> Unit
) {
    val context = LocalContext.current
    DropDownCard(
        modifier = Modifier
            .padding(20.dp),
            //.heightIn(max = 300.dp),
        header = "theme"
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            ThemeColorPicker(saveColor)
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.outline
            )
            LightDarkThemePicker(setMode)
        }
    }
}

@Composable
fun ThemeColorPicker(
    saveColor: (Int) -> Unit
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        ColorPickerDialog(
            setShowDialog = { b -> showDialog = b },
            changeColor = { color ->
                saveColor(color)
                Toast.makeText(context, "color saved", Toast.LENGTH_SHORT).show()
            })
    }
    val colors = mutableListOf<Color>(
        Color.Transparent,
        Color.White,
        Color.Black,
        Color.Blue,
        Color.Gray,
        Color.Green,
        Color.Magenta,
        Color.Red,
        Color.Yellow
    )
    LazyVerticalGrid(
        columns = GridCells.Adaptive(50.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(colors) { color ->
            IconButton(
                onClick =
                {
                    saveColor(color.toArgb())
                    Toast.makeText(context, "color saved", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .size(48.dp)
            ) {

                Surface(
                    color = color,
                    modifier = Modifier
                        .size(48.dp)
                        .border(
                            1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = CircleShape
                        ),
                    shape = CircleShape
                ) {}
            }
        }
        item {
            val colorStops = arrayOf(
                0.0f to Color.Yellow,
                0.2f to Color.Red,
                1f to Color.Blue
            )
            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .size(48.dp)
            ) {
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier
                        .size(48.dp)
                        .border(
                            1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = CircleShape
                        )
                        .background(
                            Brush.horizontalGradient(colorStops = colorStops),
                            shape = CircleShape
                        ),
                    shape = CircleShape
                ) {}
            }
        }
    }
}

@Composable
fun LightDarkThemePicker(
    setMode: (ThemeMode) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "light/dark")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            IconButton(
                onClick = { setMode(ThemeMode.AUTO) }
            ) {
                Icon(
                    Icons.Default.BrightnessAuto,
                    contentDescription = "AutoMode",
                    modifier = Modifier.size(48.dp)
                )
            }
            IconButton(
                onClick = { setMode(ThemeMode.LIGHT) }
            ) {
                Icon(
                    Icons.Default.LightMode,
                    contentDescription = "LightMode",
                    modifier = Modifier.size(48.dp)
                )
            }
            IconButton(
                onClick = { setMode(ThemeMode.NIGHT) }
            ) {
                Icon(
                    Icons.Default.ModeNight,
                    contentDescription = "ModeNight",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}