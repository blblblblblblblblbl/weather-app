package blblblbl.simplelife.weather.di.api.forecast

import blblblbl.simplelife.forecast.data.network.ForecastApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class ForecastRetrofitModule{

    @Provides
    fun provideMainFeedApi(retrofitCreator: RetrofitCreator): ForecastApi =
        retrofitCreator.createRetrofit().create(ForecastApi::class.java)
}