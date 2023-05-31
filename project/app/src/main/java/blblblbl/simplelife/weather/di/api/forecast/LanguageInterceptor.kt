package blblblbl.simplelife.weather.di.api.forecast

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale
import javax.inject.Inject


class LanguageInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
            .newBuilder()
            .addQueryParameter("lang", Locale.getDefault().getLanguage())
            .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}