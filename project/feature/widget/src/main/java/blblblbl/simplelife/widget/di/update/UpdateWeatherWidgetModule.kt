package blblblbl.simplelife.widget.di.update


import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UpdateWeatherWidgetModule {
    @Binds
    abstract fun bindUpdateWeatherWidget(updateWeatherWidgetImpl: UpdateWeatherWidgetImpl): UpdateWeatherWidget
}