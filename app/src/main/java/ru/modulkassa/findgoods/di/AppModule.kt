package ru.modulkassa.findgoods.di

import android.content.Context
import com.google.gson.Gson
import okhttp3.OkHttpClient
import ru.modulkassa.findgoods.BuildConfig
import ru.modulkassa.findgoods.domain.repository.RetailPointRepository
import toothpick.config.Module
import javax.inject.Qualifier

class AppModule(context: Context) : Module() {
    init {
        bind(Context::class.java).toInstance(context)
        bind(String::class.java).withName(DefaultModulkassaPath::class.java).toInstance(BuildConfig.MODULKASSA_ENDPOINT)
        bind(String::class.java).withName(DefaultRateAndGoodsPath::class.java).toInstance(BuildConfig.RATEANDGOODS_ENDPOINT)
        bind(Gson::class.java).toInstance(Gson())
        bind(OkHttpClient::class.java).toProvider(OkHttpClientProvider::class.java)
        bind(RetailPointRepository::class.java).toInstance(RetailPointRepository())
    }
}

@Qualifier
annotation class DefaultModulkassaPath
@Qualifier
annotation class DefaultRateAndGoodsPath