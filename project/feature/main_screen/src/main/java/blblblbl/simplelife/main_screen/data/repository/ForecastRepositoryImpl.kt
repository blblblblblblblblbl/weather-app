package blblblbl.simplelife.main_screen.data.repository

import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse
import blblblbl.simplelife.main_screen.data.persistent_storage.LastSearchPersistentStorage
import blblblbl.simplelife.main_screen.domain.repository.ForecastRepository
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val persistentStorage: LastSearchPersistentStorage
):ForecastRepository {

    override suspend fun getLast(): ForecastResponse? =
        persistentStorage.getLast()

    override suspend fun saveLast(forecast: ForecastResponse) =
        persistentStorage.addLast(forecast)
}