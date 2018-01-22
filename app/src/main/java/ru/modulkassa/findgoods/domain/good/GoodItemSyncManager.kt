package ru.modulkassa.findgoods.domain.good

import io.reactivex.Completable
import io.reactivex.Single
import ru.modulkassa.findgoods.domain.repository.RetailPointRepository
import ru.modulkassa.goods.core.api.CatalogApi
import javax.inject.Inject

interface GoodItemSyncManager {
    fun downloadItem(): Single<List<GoodItem>>
    fun addItem(item: GoodItem): Single<GoodItem>
    fun updateItem(item: GoodItem): Single<GoodItem>
}

class GoodItemSyncManagerImpl @Inject constructor(
    private val api: CatalogApi,
    private val retailPointRepo: RetailPointRepository
): GoodItemSyncManager {

    override fun downloadItem(): Single<List<GoodItem>> {
        return api.getGoods(retailPointRepo.getRetailPoint()).map {
            it.data
        }
    }

    override fun addItem(item: GoodItem): Single<GoodItem> {
        TODO("not implemented")
    }

    override fun updateItem(item: GoodItem): Single<GoodItem> {
        TODO("not implemented")
    }
}