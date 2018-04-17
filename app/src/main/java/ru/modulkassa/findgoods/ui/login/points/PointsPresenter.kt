package ru.modulkassa.findgoods.ui.login.points

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.modulkassa.findgoods.domain.point.RetailPoint
import ru.modulkassa.findgoods.domain.point.RetailPointManager
import ru.modulkassa.findgoods.domain.repository.RetailPointRepository
import javax.inject.Inject

@InjectViewState
class PointsPresenter @Inject constructor(
    private val retailPointManager: RetailPointManager,
    private val retailPointRepository: RetailPointRepository
) : MvpPresenter<PointsView>() {

    fun updatePoints() {
        viewState.showProgress()
        retailPointManager
            .getRetailPoints()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ points ->
                if (points.size == 1) {
                    saveSelected(points[0], true)
                } else {
                    viewState.updateSelection(points)
                }
                viewState.hideProgress()
            }, {
                viewState.hideProgress()
                viewState.showError()
            })
    }

    fun saveSelected(point: RetailPoint, finish: Boolean = false) {
        retailPointRepository.setSelectedPointId(point.id)
        retailPointRepository.setPointName(point.name)
        viewState.gotoGoodsScreen(finish)
    }
}