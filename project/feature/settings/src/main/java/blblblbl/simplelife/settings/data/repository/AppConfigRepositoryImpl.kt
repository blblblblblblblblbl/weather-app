package blblblbl.simplelife.settings.data.repository

import blblblbl.simplelife.settings.data.persistent_storage.AppConfigPersistentStorage
import blblblbl.simplelife.settings.data.utils.mapToData
import blblblbl.simplelife.settings.data.utils.mapToDomain
import blblblbl.simplelife.settings.domain.model.config.AppConfig
import blblblbl.simplelife.settings.domain.model.config.theme.ThemeConfig
import blblblbl.simplelife.settings.domain.model.config.theme.ThemeMode
import blblblbl.simplelife.settings.domain.model.config.weather.DegreeUnit
import blblblbl.simplelife.settings.domain.model.config.weather.SpeedUnit
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig
import blblblbl.simplelife.settings.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppConfigRepositoryImpl @Inject constructor(
    private val appConfigPersistentStorage: AppConfigPersistentStorage
) :AppConfigRepository {
    private val _config = MutableStateFlow<AppConfig?>(getConfig())
    override val config = _config.asStateFlow()

    override fun saveConfig(config: AppConfig) {
        appConfigPersistentStorage.addConfig(config.mapToData())
        _config.value = config
    }

    override fun getConfig(): AppConfig {
        val config = appConfigPersistentStorage.getConfig()?.mapToDomain()?:
        AppConfig(
            themeConfig = ThemeConfig(
                mode = ThemeMode.AUTO,
                color = 0
            ),
            weatherConfig = WeatherConfig(
                degreeUnit = DegreeUnit.C,
                speedUnit = SpeedUnit.Ms
            )
        )
        return config
    }
}