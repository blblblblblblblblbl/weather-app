package blblblbl.simplelife.cities.di

import blblblbl.simplelife.cities.data.repository.CitiesRepositoryImpl
import blblblbl.simplelife.cities.domain.repository.CitiesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindRepository(citiesRepositoryImpl: CitiesRepositoryImpl): CitiesRepository
}