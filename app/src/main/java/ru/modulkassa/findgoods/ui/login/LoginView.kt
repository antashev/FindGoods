package ru.modulkassa.findgoods.ui.login

import com.arellomobile.mvp.MvpView

interface LoginView : MvpView {
    /**
     * Показать ошибку
     */
    fun showError()

    /**
     * Показать экран выбора точки
     */
    fun showSelectScreen()
}