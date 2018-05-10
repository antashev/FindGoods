package com.antashev.rg

import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import javax.inject.Provider

class RateAndGoodsOkHttpClientProvider(
    private val token: String
) : Provider<OkHttpClient> {
    override fun get(): OkHttpClient {
        val httpClientBuilder = Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original
                    .newBuilder()
                    .header("Authorization", "Token " + token)

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