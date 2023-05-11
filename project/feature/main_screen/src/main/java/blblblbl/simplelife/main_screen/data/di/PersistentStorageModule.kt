package blblblbl.simplelife.main_screen.data.di

import blblblbl.simplelife.main_screen.data.persistent_storage.utils.StorageConverter
import blblblbl.simplelife.main_screen.data.persistent_storage.utils.StorageConverterImpl
import blblblbl.simplelife.main_screen.di.LastSearchFeature
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PersistentStorageModule{
    @Binds
    @LastSearchFeature
    abstract fun bindStorageConverter(storageConverterImpl: StorageConverterImpl): StorageConverter


}