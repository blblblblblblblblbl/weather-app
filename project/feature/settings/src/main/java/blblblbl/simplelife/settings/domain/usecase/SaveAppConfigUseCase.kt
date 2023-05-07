package blblblbl.simplelife.settings.domain.usecase


import blblblbl.simplelife.settings.domain.model.config.AppConfig
import blblblbl.simplelife.settings.domain.repository.AppConfigRepository
import javax.inject.Inject

class SaveAppConfigUseCase @Inject constructor(
    private val appConfigRepository: AppConfigRepository
) {
    suspend fun execute(config:AppConfig) =
        appConfigRepository.saveConfig(config)
}