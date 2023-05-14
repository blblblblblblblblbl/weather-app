package blblblbl.simplelife.main_screen.di

import blblblbl.simplelife.main_screen.data.repository.DataBaseRepositoryImpl
import blblblbl.simplelife.main_screen.data.repository.ForecastRepositoryImpl
import blblblbl.simplelife.main_screen.domain.repository.DataBaseRepository
import blblblbl.simplelife.main_screen.domain.repository.ForecastRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @LastSearchFeature
    abstract fun bindRepository(configRepositoryImpl: ForecastRepositoryImpl): ForecastRepository

    @Binds
    abstract fun bindDBRepository(dataBaseRepositoryImpl: DataBaseRepositoryImpl): DataBaseRepository
}