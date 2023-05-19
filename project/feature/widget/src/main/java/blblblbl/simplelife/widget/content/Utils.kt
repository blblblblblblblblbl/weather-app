package blblblbl.simplelife.widget.content

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.layout.size
import blblblbl.simplelife.settings.domain.model.config.weather.DegreeUnit
import blblblbl.simplelife.settings.domain.model.config.weather.SpeedUnit
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

@Composable
fun WeatherIcon(
    url:String,
    modifier: GlanceModifier
){
    var weatherIcon by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
    Glide.with(context)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                weatherIcon = resource//.trimBorders(Color.TRANSPARENT)
            }
            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    weatherIcon?.let {
        Image(
            provider = ImageProvider(it),
            contentDescription = "weather",
            modifier = GlanceModifier.size(48.dp)
        )
    }
}

fun Bitmap.trimBorders(color: Int): Bitmap {
    var startX = 0
    loop@ for (x in 0 until width) {
        for (y in 0 until height) {
            if (getPixel(x, y) != color) {
                startX = x
                break@loop
            }
        }
    }
    var startY = 0
    loop@ for (y in 0 until height) {
        for (x in 0 until width) {
            if (getPixel(x, y) != color) {
                startY = y
                break@loop
            }
        }
    }
    var endX = width - 1
    loop@ for (x in endX downTo 0) {
        for (y in 0 until height) {
            if (getPixel(x, y) != color) {
                endX = x
                break@loop
            }
        }
    }
    var endY = height - 1
    loop@ for (y in endY downTo 0) {
        for (x in 0 until width) {
            if (getPixel(x, y) != color) {
                endY = y
                break@loop
            }
        }
    }

    val newWidth = endX - startX + 1
    val newHeight = endY - startY + 1

    return Bitmap.createBitmap(this, startX, startY, newWidth, newHeight)
}

fun temepatureInUnits(tempC:Double,unit: DegreeUnit):String{
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
            "${speed}|kph"
        }
        SpeedUnit.Mph->{
            "${(speed/1.609).format(1)}|mph"
        }
        SpeedUnit.Ms->{
            "${(speed/3.6).format(1)}|ms"
        }
    }
}
fun Double.format(digits: Int) = "%.${digits}f".format(this)