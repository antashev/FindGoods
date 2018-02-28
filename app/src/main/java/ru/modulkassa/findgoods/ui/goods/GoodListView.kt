package ru.modulkassa.findgoods.ui.goods

import com.arellomobile.mvp.MvpView
import ru.modulkassa.findgoods.domain.good.Good

interface GoodListView: MvpView {
    fun showError(throwable: Throwable)
    fun showCantFindGoodError()
    fun updateItems(items: List<Good>)
    fun showProgress()
    fun hideProgress()
    fun hideSwipeProgress()
    fun hideLoadMoreProgress()
    fun addItems(items: List<Good>)
    fun showGoodDetail(goodJson: String)
}