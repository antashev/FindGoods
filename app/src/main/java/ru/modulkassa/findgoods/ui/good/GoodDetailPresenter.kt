package ru.modulkassa.findgoods.ui.good

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.modulkassa.findgoods.domain.good.Good
import ru.modulkassa.findgoods.domain.good.GoodItemSyncManager
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class GoodDetailPresenter @Inject constructor(
    private val goodItemSyncManager: GoodItemSyncManager
) : MvpPresenter<GoodDetailView>() {
    fun updateGood(item: Good) {
        viewState.showProgress()
        goodItemSyncManager
            .updateItem(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ good ->
                Timber.i("updated $good")
                Timber.i("old $item")
                viewState.hideProgress()
                viewState.goBack(good)
            }, {
                Timber.i("error while updating $item")
                viewState.hideProgress()
            })
    }
}