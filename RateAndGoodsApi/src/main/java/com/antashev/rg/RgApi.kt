package com.antashev.rg

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RgApi {
    @GET("product/")
    fun goods(@Query("filter", encoded = true) filter: String): Single<RateAndGoodsResponse>
}