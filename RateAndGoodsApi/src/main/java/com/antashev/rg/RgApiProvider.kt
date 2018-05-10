package com.antashev.rg

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider

class RgApiProvider private constructor(
    private val okHttpClient: OkHttpClient,
    private val gson: Gson,
    private val url: String
) : Provider<RgApi> {

    constructor(token: String, gson: Gson, url: String) : this(RateAndGoodsOkHttpClientProvider(token).get(), gson, url)

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