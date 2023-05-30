package blblblbl.simplelife.settings.data.model.config

import blblblbl.simplelife.settings.data.model.config.theme.ThemeConfig
import blblblbl.simplelife.settings.data.model.config.weather.WeatherConfig
import blblblbl.simplelife.settings.data.model.config.widget.WidgetConfig


data class AppConfig(
    val themeConfig: ThemeConfig,
    val weatherConfig: WeatherConfig,
    val widgetConfig: WidgetConfig
)