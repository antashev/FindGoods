package ru.modulkassa.findgoods.di

import android.content.Context
import com.google.gson.Gson
import okhttp3.OkHttpClient
import ru.modulkassa.findgoods.BuildConfig
import ru.modulkassa.findgoods.domain.network.api.RetailPointApi
import ru.modulkassa.findgoods.domain.point.RetailPointManager
import ru.modulkassa.findgoods.domain.point.RetailPointsImpl
import ru.modulkassa.findgoods.domain.point.di.RetailPointApiProvider
import ru.modulkassa.findgoods.domain.repository.RetailPointRepository
import toothpick.config.Module
import javax.inject.Qualifier

class AppModule(context: Context) : Module() {
    init {
        bind(Context::class.java).toInstance(context)
        bind(String::class.java).withName(DefaultModulkassaPath::class.java)
            .toInstance(BuildConfig.MODULKASSA_ENDPOINT)
        bind(String::class.java).withName(DefaultRateAndGoodsPath::class.java).toInstance(
            BuildConfig.RATEANDGOODS_ENDPOINT)
        bind(String::class.java).withName(DefaultStoragePath::class.java).toInstance(
            BuildConfig.STORAGE_ENDPOINT)
        bind(Gson::class.java).toInstance(Gson())
        bind(OkHttpClient::class.java).withName(ModulkassaClient::class.java).toProvider(
            ModulKassaOkHttpClientProvider::class.java)
        bind(OkHttpClient::class.java).withName(RateAndGoodsClient::class.java).toProvider(
            RateAndGoodsOkHttpClientProvider::class.java)
        bind(RetailPointRepository::class.java).toInstance(RetailPointRepository(context))
        bind(RetailPointApi::class.java).toProvider(RetailPointApiProvider::class.java)
        bind(RetailPointManager::class.java).to(RetailPointsImpl::class.java)
    }
}

@Qualifier
annotation class DefaultModulkassaPath

@Qualifier
annotation class DefaultRateAndGoodsPath

@Qualifier
annotation class DefaultStoragePath

@Qualifier
annotation class ModulkassaClient

@Qualifier
annotation class RateAndGoodsClient