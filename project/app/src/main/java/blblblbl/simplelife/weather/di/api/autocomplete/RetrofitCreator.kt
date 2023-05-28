package blblblbl.simplelife.weather.di.api.autocomplete

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitCreator @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authorizationInterceptor: ApiKeyInterceptorAC
) {
    private val BASE_URL = "https://api.geoapify.com/v1/geocode/"
    private val gson = GsonBuilder().setLenient().create()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                //.addInterceptor(MockRequestInterceptor(context))
                .addInterceptor(authorizationInterceptor)
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    fun createRetrofit():Retrofit{
        val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(60L, TimeUnit.SECONDS)
                    .readTimeout(60L, TimeUnit.SECONDS)
                    //.addInterceptor(MockRequestInterceptor(context))
                    .addInterceptor(authorizationInterceptor)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
        return builder.build()
    }
}