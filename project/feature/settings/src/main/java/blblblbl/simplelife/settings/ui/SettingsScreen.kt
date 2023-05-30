package blblblbl.simplelife.settings.ui


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.settings.domain.model.config.AppConfig


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
                modifier = Modifier.padding(20.dp),
                initial = weatherConfig,
                setConfig = {
                    saveConfig(config.copy(weatherConfig = it)) }
            )
        }
        config?.widgetConfig?.let { widgetConfig->
            WidgetConfigPicker(
                modifier = Modifier.padding(20.dp),
                initial = widgetConfig,
                setConfig = {
                    saveConfig(config.copy(widgetConfig = it))
                }
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
                        //.fillMaxHeight()
                ) {
                    content()
                }
            }

        }

    }
}