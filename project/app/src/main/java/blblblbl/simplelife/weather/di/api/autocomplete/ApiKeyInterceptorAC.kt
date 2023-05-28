package blblblbl.simplelife.weather.di.api.autocomplete

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject


class ApiKeyInterceptorAC @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url()
            .newBuilder()
            .addQueryParameter("apiKey", API_KEY)
            .addQueryParameter("format","json")
            .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
    companion object{
        const val API_KEY = "f37f21baf7a84a29a0bbefb7da6dfb10"
    }
}