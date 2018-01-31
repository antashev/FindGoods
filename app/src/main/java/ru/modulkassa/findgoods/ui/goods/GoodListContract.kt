package ru.modulkassa.findgoods.ui.goods

import com.arellomobile.mvp.MvpView
import ru.modulkassa.findgoods.domain.good.Good

interface GoodListView: MvpView {
    fun showError(throwable: Throwable)
    fun updateItems(items: List<Good>)
    fun showProgress()
    fun hideSwipeProgress()
    fun hideLoadMoreProgress()
    fun addItems(items: List<Good>)
}