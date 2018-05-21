package ru.modulkassa.findgoods.domain.good.di

import com.antashev.rg.RgApi
import com.antashev.rg.RgApiProvider
import com.google.gson.Gson
import ru.modulkassa.findgoods.BuildConfig
import ru.modulkassa.findgoods.domain.good.FindGoods
import ru.modulkassa.findgoods.domain.good.FindGoodsImpl
import ru.modulkassa.findgoods.domain.network.api.StorageApi
import toothpick.config.Module

class FindGoodsModule: Module() {
    init {
        bind(RgApi::class.java)
            .toInstance(RgApiProvider(BuildConfig.RATEANDGOODS_TOKEN, Gson(),
                BuildConfig.RATEANDGOODS_ENDPOINT, BuildConfig.DEBUG).get())
        bind(FindGoods::class.java).to(
            FindGoodsImpl::class.java)
        bind(StorageApi::class.java).toProvider(
            StorageApiProvider::class.java
        )
    }
}