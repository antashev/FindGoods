package ru.modulkassa.findgoods.ui.login

import com.arellomobile.mvp.MvpView

interface LoginView : MvpView {
    fun showError()
    fun showSelectScreen()
}