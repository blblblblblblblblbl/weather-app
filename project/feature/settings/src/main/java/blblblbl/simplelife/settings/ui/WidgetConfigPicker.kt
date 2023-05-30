package blblblbl.simplelife.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.settings.domain.model.config.widget.WidgetConfig

@Composable
fun WidgetConfigPicker(
    modifier: Modifier = Modifier,
    initial: WidgetConfig,
    setConfig: (WidgetConfig) -> Unit
) {
    DropDownCard(
    modifier = modifier,
    header = "widget",
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "update time")
                Spacer(modifier = Modifier.weight(1f))
                UpdateTimePicker(
                    initial = initial.updateTime,
                    onSelect = {time->
                        setConfig(initial.copy(updateTime = time))
                    }
                )
            }
        }
    }
}
@Composable
fun UpdateTimePicker(
    initial:Long,
    onSelect:(Long)->Unit
){
    DropDownPicker<Long>(variants = arrayOf(15,30,60), initial = initial, onSelected = onSelect)
}