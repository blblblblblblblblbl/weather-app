package blblblbl.simplelife.cities.data.di

import blblblbl.simplelife.cities.data.datasource.CitiesDataSource
import blblblbl.simplelife.cities.data.datasource.CitiesDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindHistoryDataSource(citiesDataSourceImpl: CitiesDataSourceImpl): CitiesDataSource
}