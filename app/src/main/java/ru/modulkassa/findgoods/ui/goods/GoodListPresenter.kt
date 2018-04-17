package ru.modulkassa.findgoods.ui.goods

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.modulkassa.findgoods.domain.good.FindGoods
import ru.modulkassa.findgoods.domain.good.Good
import ru.modulkassa.findgoods.domain.good.GoodItemSyncManager
import ru.modulkassa.findgoods.domain.repository.RetailPointRepository
import javax.inject.Inject

@InjectViewState
class GoodListPresenter @Inject constructor(
    private val goodItemSyncManager: GoodItemSyncManager,
    private val findGoods: FindGoods,
    private val gson: Gson,
    private val repository: RetailPointRepository
): MvpPresenter<GoodListView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showPointName(repository.selectedPointName())
        viewState.showProgress()
        goodItemSyncManager.downloadItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.updateItems(it)
                viewState.hideProgress()
            },{
                viewState.hideProgress()
                viewState.showError(it)
            })
    }

    fun refreshItems() {
        goodItemSyncManager.downloadItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.updateItems(it)
                viewState.hideSwipeProgress()
            },{
                viewState.hideSwipeProgress()
                viewState.showError(it)
            })
    }

    fun downloadNext() {
        goodItemSyncManager.downloadNextItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.addItems(it)
                viewState.hideLoadMoreProgress()
            },{
                viewState.hideLoadMoreProgress()
                viewState.showError(it)
            })
    }

    fun needDownloadMore(totalItemCount: Int): Boolean {
        return totalItemCount < goodItemSyncManager.getTotalCount()
    }

    fun showDetail(good: Good) {
        viewState.showGoodDetail(gson.toJson(good))
    }

    fun findGood(barcode: String) {
        viewState.showProgress()
        findGoods.find(barcode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ good ->
                viewState.hideProgress()
                showDetail(good)
            },{
                viewState.hideProgress()
                viewState.showError(it)
            })
    }

}