package ru.modulkassa.findgoods.domain.good.di

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.modulkassa.findgoods.di.DefaultStoragePath
import ru.modulkassa.findgoods.di.ModulkassaClient
import ru.modulkassa.findgoods.domain.network.api.StorageApi
import javax.inject.Inject
import javax.inject.Provider

class StorageApiProvider @Inject constructor(
    @ModulkassaClient val okHttpClient: OkHttpClient,
    private val gson: Gson,
    @DefaultStoragePath private val url: String
) : Provider<StorageApi> {
    override fun get(): StorageApi {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(StorageApi::class.java)
    }
}