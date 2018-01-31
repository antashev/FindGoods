package ru.modulkassa.findgoods.domain.good

import io.reactivex.Single
import ru.modulkassa.findgoods.domain.network.dto.GoodItemResponse
import ru.modulkassa.findgoods.domain.repository.RetailPointRepository
import ru.modulkassa.goods.core.api.CatalogApi
import timber.log.Timber
import javax.inject.Inject

interface GoodItemSyncManager {
    fun downloadItems(): Single<List<Good>>
    fun downloadNextItems(): Single<List<Good>>
    fun addItem(item: Good): Single<Good>
    fun updateItem(item: Good): Single<Good>
}

class GoodItemSyncManagerImpl @Inject constructor(
    private val api: CatalogApi,
    private val retailPointRepo: RetailPointRepository
): GoodItemSyncManager {
    companion object {
        const val BULK_SIZE = 30
    }
    var totalCount: Long = 0
    var currentPos: Int = 0

    override fun downloadItems(): Single<List<Good>> {
        return download(0)
    }

    override fun downloadNextItems(): Single<List<Good>> {
        return download(currentPos)
    }

    override fun addItem(item: Good): Single<Good> {
        TODO("not implemented")
    }

    override fun updateItem(item: Good): Single<Good> {
        TODO("not implemented")
    }

    private fun download(start: Int): Single<List<Good>> {
        return api
            .getGoods(retailPointRepo.getRetailPoint(), start, BULK_SIZE)
            .map {
                totalCount = it.totalCount
                it.data
            }
            .doOnSuccess {
                currentPos += BULK_SIZE
            }
    }
}