package ru.modulkassa.findgoods.ui.good

import com.arellomobile.mvp.MvpView
import ru.modulkassa.findgoods.domain.good.Good

interface GoodDetailView : MvpView {
    fun showError(throwable: Throwable)
    fun goBack(good: Good)
    fun showProgress()
    fun hideProgress()
}