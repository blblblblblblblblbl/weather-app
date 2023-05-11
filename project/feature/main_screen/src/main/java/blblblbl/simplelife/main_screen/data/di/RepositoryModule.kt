package blblblbl.simplelife.main_screen.data.di

import blblblbl.simplelife.main_screen.data.persistent_storage.LastSearchPersistentStorage
import blblblbl.simplelife.main_screen.data.persistent_storage.LastSearchPersistentStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindPersistentStorage(persistentStorageImpl: LastSearchPersistentStorageImpl): LastSearchPersistentStorage

}