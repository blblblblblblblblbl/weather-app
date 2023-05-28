package blblblbl.simplelife.autocomplete.di

import blblblbl.simplelife.autocomplete.data.datasource.ACDatasource
import blblblbl.simplelife.autocomplete.data.datasource.ACDatasourceImpl
import blblblbl.simplelife.autocomplete.data.repository.AutocompleteRepositoryImpl
import blblblbl.simplelife.autocomplete.domain.repository.AutocompleteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class AutoCompleteModule {

    @Binds
    abstract fun bindDataSource(acDatasourceImpl: ACDatasourceImpl): ACDatasource
    @Binds
    abstract fun bindRepository(autocompleteRepository: AutocompleteRepositoryImpl): AutocompleteRepository
}
