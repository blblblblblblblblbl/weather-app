package blblblbl.simplelife.settings.domain.usecase

import blblblbl.simplelife.settings.domain.model.config.AppConfig
import blblblbl.simplelife.settings.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAppConfigUseCase @Inject constructor(
    private val appConfigRepository: AppConfigRepository
) {
    suspend fun getAppConfig():AppConfig =
        appConfigRepository.getConfig()
    fun getAppConfigFlow():StateFlow<AppConfig?> =
        appConfigRepository.config
}