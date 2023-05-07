package blblblbl.simplelife.settings.di

import blblblbl.simplelife.settings.data.repository.AppConfigRepositoryImpl
import blblblbl.simplelife.settings.domain.repository.AppConfigRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindRepository(configRepositoryImpl: AppConfigRepositoryImpl): AppConfigRepository
}