package ru.modulkassa.findgoods.domain.good.di

import ru.modulkassa.findgoods.domain.good.CatalogJsonStream
import ru.modulkassa.findgoods.domain.good.CommandExecutor
import ru.modulkassa.findgoods.domain.good.GoodItemSyncManager
import ru.modulkassa.findgoods.domain.good.GoodItemSyncManagerImpl
import ru.modulkassa.findgoods.domain.repository.GoodItemRepository
import ru.modulkassa.goods.core.api.CatalogApi
import toothpick.config.Module

class GoodItemSyncModule : Module() {
    init {
        bind(CatalogApi::class.java).toProvider(
            CatalogApiProvider::class.java)
        bind(GoodItemSyncManager::class.java).to(
            GoodItemSyncManagerImpl::class.java)
        bind(GoodItemRepository::class.java).toInstance(GoodItemRepository())
        bind(CatalogJsonStream::class.java)
        bind(CommandExecutor::class.java)
    }
}