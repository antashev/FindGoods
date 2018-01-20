package ru.modulkassa.findgoods.ui.goods

import com.arellomobile.mvp.MvpView
import ru.modulkassa.findgoods.domain.good.GoodItem

interface GoodListView: MvpView {
    fun showError(throwable: Throwable)
    fun updateItems(items: List<GoodItem>)
}