package ru.modulkassa.findgoods.domain.good

import io.reactivex.Single
import ru.modulkassa.findgoods.domain.repository.RetailPointRepository
import ru.modulkassa.goods.core.api.CatalogApi
import javax.inject.Inject

interface GoodItemSyncManager {
    fun downloadItems(): Single<List<Good>>
    fun downloadNextItems(): Single<List<Good>>
    fun addItem(item: Good): Single<Good>
    fun updateItem(item: Good): Single<Good>
    fun getTotalCount(): Long
}

class GoodItemSyncManagerImpl @Inject constructor(
    private val api: CatalogApi,
    private val retailPointRepo: RetailPointRepository
): GoodItemSyncManager {
    companion object {
        const val BULK_SIZE = 20
    }
    var total: Long = 0
    var currentPos: Int = 0

    override fun downloadItems(): Single<List<Good>> {
        currentPos = 0
        return download(currentPos)
    }

    override fun downloadNextItems(): Single<List<Good>> {
        return download(currentPos)
    }

    override fun addItem(item: Good): Single<Good> {
        TODO("not implemented")
    }

    override fun updateItem(item: Good): Single<Good> {
        return api
            .updateGood(retailPointRepo.selectedPointId(), item)
            .map { it.catalogEntity.toDomain() }
    }

    override fun getTotalCount(): Long {
        return total
    }

    private fun download(start: Int): Single<List<Good>> {
        return api
            .getGoods(retailPointRepo.selectedPointId(), start, BULK_SIZE)
            .map {
                total = it.totalCount
                it.data
            }
            .doOnSuccess {
                currentPos += BULK_SIZE
            }
    }
}