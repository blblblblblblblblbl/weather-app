package blblblbl.simplelife.weather.di.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class ApiKeyInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("key", API_KEY)
            .build()
        return chain.proceed(request)
    }
    companion object{
        const val API_KEY = "5f6574d1b7f94bd99d042815232501"
    }
}