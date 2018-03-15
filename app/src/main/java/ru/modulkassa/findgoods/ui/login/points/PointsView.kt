package ru.modulkassa.findgoods.ui.login.points

import com.arellomobile.mvp.MvpView
import ru.modulkassa.findgoods.domain.point.RetailPoint

interface PointsView : MvpView {
    fun updateSelection(points: List<RetailPoint>)
    fun showError()
    fun gotoGoodsScreen(finish: Boolean)
    fun showProgress()
    fun hideProgress()
}