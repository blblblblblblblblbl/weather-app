package blblblbl.simplelife.widget.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceTheme
import androidx.glance.text.FontWeight
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

object Typography{
    val headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    )
    val headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )
    val headlineSmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
    val labelLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
    val labelMedium = TextStyle(
        fontSize = 16.sp
    )
    val bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    )
    val bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    val bodySmall = TextStyle(
        fontSize = 10.sp
    )
}
object WeatherWidgetTheme{
    val typography = Typography
}

@Composable
fun WeatherWidgetTheme(
    content: @Composable () -> Unit
){
    GlanceTheme(
        content = content
    )
}