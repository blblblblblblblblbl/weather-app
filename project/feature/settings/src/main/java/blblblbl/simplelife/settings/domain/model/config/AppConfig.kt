package blblblbl.simplelife.settings.domain.model.config

import blblblbl.simplelife.settings.domain.model.config.theme.ThemeConfig
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig

data class AppConfig(
    val themeConfig: ThemeConfig,
    val weatherConfig: WeatherConfig
)