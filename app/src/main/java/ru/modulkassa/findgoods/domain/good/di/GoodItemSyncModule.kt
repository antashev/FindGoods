package ru.modulkassa.findgoods.domain.good.di

import ru.modulkassa.findgoods.domain.good.GoodItemSyncManager
import ru.modulkassa.findgoods.domain.good.GoodItemSyncManagerImpl
import ru.modulkassa.goods.core.api.CatalogApi
import toothpick.config.Module

class GoodItemSyncModule : Module() {
    init {
        bind(CatalogApi::class.java).toProvider(
            CatalogApiProvider::class.java)
        bind(GoodItemSyncManager::class.java).to(
            GoodItemSyncManagerImpl::class.java)
    }
}