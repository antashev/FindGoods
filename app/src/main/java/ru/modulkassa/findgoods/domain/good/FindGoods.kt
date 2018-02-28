package ru.modulkassa.findgoods.domain.good

import io.reactivex.Single
import ru.modulkassa.findgoods.domain.good.AlcoholType.ALCOHOL
import ru.modulkassa.findgoods.domain.good.AlcoholType.NO_ALCOHOL
import ru.modulkassa.findgoods.domain.network.api.StorageApi
import ru.modulkassa.goods.core.api.RgApi
import java.net.URLEncoder
import javax.inject.Inject

interface FindGoods {
    fun find(barcode: String): Single<Good>
}

class FindGoodsImpl @Inject constructor(
    private val rgApi: RgApi,
    private val storageApi: StorageApi
) : FindGoods {
    override fun find(barcode: String): Single<Good> {
        return storageApi
            .getGood(barcode)
            .flatMap {
                if (it.isNotEmpty())
                    Single.just(it[0])
                else
                    Single.error(Exception())
            }
            .onErrorResumeNext {
                val item = URLEncoder.encode("{\"gtin\":\"$barcode\"}", "utf-8")
                rgApi
                    .goods(item)
                    .flatMap {
                        if (it.results.isNotEmpty()) {
                            val alcoholType = if (it.results[0].alcohol) ALCOHOL else NO_ALCOHOL
                            Single.just(
                                Good(
                                    name = it.results[0].title,
                                    barcode = barcode,
                                    inventCode = barcode,
                                    alcoholType = alcoholType
                                )
                            )
                        } else {
                            Single.error(Exception())
                        }
                    }
            }
    }

}