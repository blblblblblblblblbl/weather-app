package blblblbl.simplelife.weather.di.api.autocomplete


import blblblbl.simplelife.autocomplete.data.network.ACApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class ACRetrofitModule{
    @Provides
    fun provideACApi(retrofitCreator: RetrofitCreator): ACApi =
        retrofitCreator.createRetrofit().create(ACApi::class.java)
}