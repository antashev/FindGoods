package ru.modulkassa.goods.core.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.modulkassa.findgoods.domain.network.dto.RateAndGoodsResponse

interface RgApi {
    @GET("product/")
    fun goods(@Query("filter", encoded = true) filter: String): Single<RateAndGoodsResponse>
}