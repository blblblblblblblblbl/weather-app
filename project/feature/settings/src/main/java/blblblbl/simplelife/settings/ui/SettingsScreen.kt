package blblblbl.simplelife.settings.ui


import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import blblblbl.simplelife.settings.domain.model.config.AppConfig
import blblblbl.simplelife.settings.domain.model.config.theme.ThemeMode
import blblblbl.simplelife.settings.domain.model.config.weather.DegreeUnit
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController


@Composable
fun SettingsScreen(
    config: AppConfig?,
    saveConfig: (AppConfig) -> Unit
) {
    val context = LocalContext.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        ThemePicker(
            saveColor = { color ->
                if (config != null) {
                    saveConfig(config.copy(themeConfig = config.themeConfig.copy(color = color)))
                }
            },
            setMode = { mode ->
                if (config != null) {
                    saveConfig(config.copy(themeConfig = config.themeConfig.copy(mode = mode)))
                }
            }
        )
        config?.weatherConfig?.let {weatherConfig->
            WeatherConfigPicker(
                initial = weatherConfig,
                setConfig = {
                    saveConfig(config.copy(weatherConfig = it)) }
            )
        }
    }
}




@Composable
fun DropDownCard(
    modifier: Modifier = Modifier,
    header: String = "",
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Card(modifier = modifier) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = header)
                Spacer(modifier = Modifier.weight(1F))
                IconButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "expand")
                }
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
                        .fillMaxHeight()
                ) {
                    content()
                }
            }

        }

    }
}