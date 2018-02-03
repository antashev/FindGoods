package ru.modulkassa.findgoods.di

import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import ru.modulkassa.findgoods.BuildConfig
import ru.modulkassa.findgoods.domain.repository.RetailPointRepository
import javax.inject.Inject
import javax.inject.Provider

// todo без инжекта не создается фабрика. Надо разобратсья
class RateAndGoodsOkHttpClientProvider @Inject constructor(
    private val retailPointRepository: RetailPointRepository
) : Provider<OkHttpClient> {
    override fun get(): OkHttpClient {
        val httpClientBuilder = Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original
                    .newBuilder()
                    .header("Authorization", "Token " + BuildConfig.RATEANDGOODS_TOKEN)

                val request = requestBuilder.build()
                chain.proceed(request)
            }

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = BODY
            httpClientBuilder.addNetworkInterceptor(httpLoggingInterceptor)
        }
        return httpClientBuilder.build()
    }
}