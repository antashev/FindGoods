package ru.modulkassa.findgoods.domain.good

import io.reactivex.Single
import ru.modulkassa.findgoods.domain.repository.RetailPointRepository
import ru.modulkassa.goods.core.api.CatalogApi
import javax.inject.Inject

interface GoodItemSyncManager {
    fun downloadItem(): Single<List<Good>>
    fun addItem(item: Good): Single<Good>
    fun updateItem(item: Good): Single<Good>
}

class GoodItemSyncManagerImpl @Inject constructor(
    private val api: CatalogApi,
    private val retailPointRepo: RetailPointRepository
): GoodItemSyncManager {

    override fun downloadItem(): Single<List<Good>> {
        return api.getGoods(retailPointRepo.getRetailPoint()).map {
            it.data
        }
    }

    override fun addItem(item: Good): Single<Good> {
        TODO("not implemented")
    }

    override fun updateItem(item: Good): Single<Good> {
        TODO("not implemented")
    }
}