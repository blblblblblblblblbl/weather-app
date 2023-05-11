package blblblbl.simplelife.main_screen.data.persistent_storage.di


import blblblbl.simplelife.main_screen.data.persistent_storage.utils.GsonParser
import blblblbl.simplelife.main_screen.data.persistent_storage.utils.JsonParser
import blblblbl.simplelife.main_screen.di.LastSearchFeature
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class StorageConverterModule {

    @Provides
    @LastSearchFeature
    fun provideGson(): Gson {
        val gson = GsonBuilder().setLenient().create()
        return gson
    }

    @Provides
    @LastSearchFeature
    fun provideJsonParser(@LastSearchFeature gson:Gson): JsonParser = GsonParser(gson)

}