package blblblbl.simplelife.settings.data.di

import blblblbl.simplelife.settings.data.persistent_storage.AppConfigPersistentStorage
import blblblbl.simplelife.settings.data.persistent_storage.AppConfigPersistentStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindPersistentStorage(persistentStorageImpl: AppConfigPersistentStorageImpl): AppConfigPersistentStorage

}