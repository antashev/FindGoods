package ru.modulkassa.findgoods.domain.good.di

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.modulkassa.findgoods.di.DefaultRateAndGoodsPath
import ru.modulkassa.findgoods.di.RateAndGoodsClient
import ru.modulkassa.goods.core.api.RgApi
import javax.inject.Inject
import javax.inject.Provider

class RgApiProvider @Inject constructor(
    @RateAndGoodsClient private val okHttpClient: OkHttpClient,
    private val gson: Gson,
    @DefaultRateAndGoodsPath private val url: String
) : Provider<RgApi> {
    override fun get(): RgApi {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RgApi::class.java)
    }
}