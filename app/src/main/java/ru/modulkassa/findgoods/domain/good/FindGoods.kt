package ru.modulkassa.findgoods.domain.good

import io.reactivex.Single
import ru.modulkassa.findgoods.domain.network.api.StorageApi
import ru.modulkassa.goods.core.api.RgApi
import java.net.URLEncoder
import javax.inject.Inject

interface FindGoods {
    fun find(barcode: String): Single<String>
}

class FindGoodsImpl @Inject constructor(
    private val rgApi: RgApi,
    private val storageApi: StorageApi
) : FindGoods {
    override fun find(barcode: String): Single<String> {
        return storageApi
            .getGood(barcode)
            .flatMap {
                if (it.isNotEmpty())
                    Single.just(it[0].name)
                else
                    Single.error(Exception())
            }
            .onErrorResumeNext {
                val item = URLEncoder.encode("{\"gtin\":\"$barcode\"}", "utf-8")
                rgApi
                    .goods(item)
                    .map {
                        if (it.results.isNotEmpty()) it.results[0].title else ""
                    }
            }
    }

}