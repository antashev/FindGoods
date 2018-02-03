package ru.modulkassa.findgoods.domain.network.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.modulkassa.findgoods.domain.good.Good

interface StorageApi {
    @GET("v1/inventory")
    fun getGood(@Query("barcode") barcode: String): Single<List<Good>>
}