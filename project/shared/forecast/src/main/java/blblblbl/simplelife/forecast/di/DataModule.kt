package blblblbl.simplelife.forecast.di


import blblblbl.simplelife.forecast.data.repository.ForecastRepositoryImpl
import blblblbl.simplelife.forecast.domain.repository.ForecastRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule{
    @Binds
    abstract fun bindPersistentStorage(forecastRepositoryImpl: ForecastRepositoryImpl): ForecastRepository

}