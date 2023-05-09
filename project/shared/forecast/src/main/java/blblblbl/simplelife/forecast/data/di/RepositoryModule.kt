package blblblbl.simplelife.forecast.data.di

import blblblbl.simplelife.forecast.data.datasource.ForecastDataSource
import blblblbl.simplelife.forecast.data.datasource.ForecastDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent



@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindPersistentStorage(forecastDataSourceImpl: ForecastDataSourceImpl): ForecastDataSource

}