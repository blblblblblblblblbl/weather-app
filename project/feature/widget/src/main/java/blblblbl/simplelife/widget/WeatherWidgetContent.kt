package blblblbl.simplelife.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.padding
import androidx.glance.text.Text
import blblblbl.simplelife.widget.WidgetKeys.Prefs.cityNamePK
import blblblbl.simplelife.widget.WidgetKeys.Prefs.forecastJSONPK

@Composable
fun WeatherWidgetContent(prefs:Preferences){
    val cityName = prefs[cityNamePK]?:"city"
    val forecast = prefs[forecastJSONPK].orEmpty()
    Column(
        modifier = GlanceModifier
            .background(imageProvider = ImageProvider(R.drawable.widget_background))
            .appWidgetBackground()
            .padding(16.dp)
    ) {
        Text(text = cityName)
        Text(text = forecast)
    }

}