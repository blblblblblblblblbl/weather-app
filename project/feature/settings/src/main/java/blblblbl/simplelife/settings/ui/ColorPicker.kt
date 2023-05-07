package blblblbl.simplelife.settings.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun ColorPickerDialog(
    setShowDialog: (Boolean) -> Unit,
    changeColor: (Int) -> Unit
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(shape = MaterialTheme.shapes.extraLarge) {
            CustomColorPicker(
                changeColor = changeColor,
                modifier = Modifier
                    .padding(20.dp)
            )
        }
    }
}

@Composable
fun CustomColorPicker(
    modifier: Modifier = Modifier,
    changeColor: (Int) -> Unit
) {
    val controller = rememberColorPickerController()
    var color by remember { mutableStateOf<Color>(Color.White) }
    Box(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = color,
                modifier = Modifier
                    .requiredHeight(100.dp)
                    .border(2.dp, Color.Black, MaterialTheme.shapes.extraLarge)
                    .fillMaxWidth()
            ) {}
            HsvColorPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .padding(10.dp),
                controller = controller,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    color = colorEnvelope.color
                }
            )
            Button(onClick = {
                changeColor(color.toArgb())
            }) {
                Text(text = "set color")
            }
        }
    }
}