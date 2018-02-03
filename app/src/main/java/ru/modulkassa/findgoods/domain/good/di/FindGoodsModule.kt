package ru.modulkassa.findgoods.domain.good.di

import ru.modulkassa.findgoods.domain.good.FindGoods
import ru.modulkassa.findgoods.domain.good.FindGoodsImpl
import ru.modulkassa.findgoods.domain.network.api.StorageApi
import ru.modulkassa.goods.core.api.RgApi
import toothpick.config.Module

class FindGoodsModule : Module() {
    init {
        bind(RgApi::class.java).toProvider(
            RgApiProvider::class.java)
        bind(FindGoods::class.java).to(
            FindGoodsImpl::class.java)
        bind(StorageApi::class.java).toProvider(
            StorageApiProvider::class.java
        )
    }
}