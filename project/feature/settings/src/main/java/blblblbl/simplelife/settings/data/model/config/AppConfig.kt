package blblblbl.simplelife.settings.data.model.config

import blblblbl.simplelife.settings.data.model.config.theme.ThemeConfig
import blblblbl.simplelife.settings.data.model.config.weather.WeatherConfig


data class AppConfig(
    val themeConfig: ThemeConfig,
    val weatherConfig: WeatherConfig
)