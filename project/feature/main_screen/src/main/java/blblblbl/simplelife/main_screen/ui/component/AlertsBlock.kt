package blblblbl.simplelife.main_screen.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import blblblbl.simplelife.forecast.domain.model.forecast.Alerts

/**
 * @author Kirill Tolmachev 04.08.2023
 */

@Composable
fun AlertsBlock(
    modifier: Modifier = Modifier,
    alerts: Alerts
){
    if(alerts.alert.isNotEmpty()){
        Surface(
            modifier = modifier,
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.error
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                alerts.alert.forEach{alert->
                    alert.event?.let { Text(text = it) }
                }
            }
        }
    }
}