package ru.modulkassa.findgoods.ui.goods

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.modulkassa.findgoods.domain.good.GoodItemSyncManager
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class GoodListPresenter @Inject constructor(
    private val goodItemSyncManager: GoodItemSyncManager
): MvpPresenter<GoodListView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        goodItemSyncManager.downloadItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.updateItems(it)
            },{
               Timber.i("Error $it")
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
                Timber.i("Error $it")
            })
    }

    fun downloadNext() {
        Timber.i("---------------------next")
        goodItemSyncManager.downloadNextItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.addItems(it)
                viewState.hideLoadMoreProgress()
            },{
                Timber.i("Error $it")
            })
    }


}