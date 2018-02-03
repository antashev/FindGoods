package ru.modulkassa.findgoods.domain.point.di

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import ru.modulkassa.findgoods.di.DefaultModulkassaPath
import ru.modulkassa.findgoods.di.ModulkassaClient
import ru.modulkassa.findgoods.domain.network.api.RetailPointApi
import javax.inject.Inject
import javax.inject.Provider

class RetailPointApiProvider @Inject constructor(
    @ModulkassaClient private val okHttpClient: OkHttpClient,
    private val gson: Gson,
    @DefaultModulkassaPath private val url: String
) : Provider<RetailPointApi> {
    override fun get(): RetailPointApi {
        return Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RetailPointApi::class.java)
    }
}