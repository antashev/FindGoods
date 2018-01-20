package ru.modulkassa.findgoods.domain.good.di

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.modulkassa.findgoods.di.DefaultModulkassaPath
import ru.modulkassa.goods.core.api.CatalogApi
import javax.inject.Inject
import javax.inject.Provider

class CatalogApiProvider @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val gson: Gson,
    @DefaultModulkassaPath private val url: String
) : Provider<CatalogApi> {
    override fun get(): CatalogApi {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CatalogApi::class.java)
    }
}