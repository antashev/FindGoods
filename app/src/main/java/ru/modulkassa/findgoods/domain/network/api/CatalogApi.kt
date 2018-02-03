package ru.modulkassa.goods.core.api

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import ru.modulkassa.findgoods.domain.good.Good
import ru.modulkassa.findgoods.domain.network.dto.CatalogResponse
import ru.modulkassa.findgoods.domain.network.dto.GoodItemResponse

interface CatalogApi {

    @GET("v1/retail-point/{retailPointId}/catalog/INVENTORY")
    fun getGoods(@Path("retailPointId") retailPointId: String,
                 @Query("start") start: Int,
                 @Query("count") count: Int): Single<GoodItemResponse>

    @POST("v1/retail-point/{retailPointId}/catalog")
    fun addGood(@Path("retailPointId") retailPointId: String, @Body item: Good): Single<Good>

    @PUT("v1/retail-point/{retailPointId}/catalog")
    fun updateGood(@Path(
        "retailPointId") retailPointId: String, @Body item: Good): Single<CatalogResponse>

}