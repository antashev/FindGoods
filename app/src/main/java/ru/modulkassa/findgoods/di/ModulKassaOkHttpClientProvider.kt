package ru.modulkassa.findgoods.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import ru.modulkassa.findgoods.BuildConfig
import ru.modulkassa.findgoods.domain.network.AuthorizationInterceptor
import ru.modulkassa.findgoods.domain.repository.RetailPointRepository
import javax.inject.Inject
import javax.inject.Provider

class ModulKassaOkHttpClientProvider @Inject constructor(
    private val retailPointRepository: RetailPointRepository
) : Provider<OkHttpClient> {
    override fun get(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor(retailPointRepository))

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = Level.BODY
            httpClientBuilder.addNetworkInterceptor(httpLoggingInterceptor)
        }
        return httpClientBuilder.build()
    }
}