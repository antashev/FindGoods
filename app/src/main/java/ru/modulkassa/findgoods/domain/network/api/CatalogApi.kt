package ru.modulkassa.goods.core.api

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming
import ru.modulkassa.findgoods.domain.good.GoodItem

interface CatalogApi {

    /**
     * Получить каталог
     * @param retailPointId идентификатор торговой точки
     * @param catalogs список наименований каталогов через зяпятую, например: `"A,B,C"`
     * @return поток файла каталога
     */
    @Streaming
    @GET("v2/retail-point/{retailPointId}/catalog")
    fun getGoods(@Path("retailPointId") retailPointId: String, @Query("catalogNames") catalogs: String = "INVENTORY"): Single<ResponseBody>

    @POST("v1/retail-point/{retailPointId}/catalog")
    fun addGood(@Path("retailPointId") retailPointId: String, @Body item: GoodItem): Single<GoodItem>

    @PUT("v1/retail-point/{retailPointId}/catalog")
    fun updateGood(@Path("retailPointId") retailPointId: String, @Body item: GoodItem): Single<GoodItem>

}