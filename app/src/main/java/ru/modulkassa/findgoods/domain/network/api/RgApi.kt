package ru.modulkassa.goods.core.api

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface RgApi {
    //@Headers("Authorization: Token 368d9e1829574438749c7233abf89e1bc03c06e4")
    @GET("product/")
    fun goods(@Query("filter", encoded = true) filter: String): Single<ResponseBody>
}