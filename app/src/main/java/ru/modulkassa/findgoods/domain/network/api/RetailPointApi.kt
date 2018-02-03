package ru.modulkassa.findgoods.domain.network.api

import io.reactivex.Single
import retrofit2.http.GET
import ru.modulkassa.findgoods.domain.point.RetailPoint

interface RetailPointApi {
    @GET("v2/retail-points")
    fun retailPoints(): Single<List<RetailPoint>>
}