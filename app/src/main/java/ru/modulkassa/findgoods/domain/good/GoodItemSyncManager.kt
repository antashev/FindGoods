package ru.modulkassa.findgoods.domain.good

import io.reactivex.Completable
import io.reactivex.Single
import ru.modulkassa.findgoods.domain.repository.RetailPointRepository
import ru.modulkassa.goods.core.api.CatalogApi
import javax.inject.Inject

interface GoodItemSyncManager {
    fun downloadItem(): Completable
    fun addItem(item: GoodItem): Single<GoodItem>
    fun updateItem(item: GoodItem): Single<GoodItem>
}

class GoodItemSyncManagerImpl @Inject constructor(
    private val api: CatalogApi,
    private val retailPointRepo: RetailPointRepository,
    private val commandExecutor: CommandExecutor,
    private val catalogJsonStream: CatalogJsonStream
): GoodItemSyncManager {
    override fun downloadItem(): Completable {
        return api.getGoods(retailPointRepo.getRetailPoint())
            .flatMapPublisher { response ->
                catalogJsonStream.toFlowable(response.source().inputStream())
            }
            .map(commandExecutor::execute)
            .flatMapCompletable { Completable.complete() }
    }

    override fun addItem(item: GoodItem): Single<GoodItem> {
        TODO("not implemented")
    }

    override fun updateItem(item: GoodItem): Single<GoodItem> {
        TODO("not implemented")
    }
}