package ru.modulkassa.findgoods.ui.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.modulkassa.findgoods.domain.point.RetailPointManager
import ru.modulkassa.findgoods.domain.repository.RetailPointRepository
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor(
    private val retailPoints: RetailPointManager,
    private val retailPointRepository: RetailPointRepository
) : MvpPresenter<LoginView>() {

    fun singIn(login: String, password: String) {
        retailPointRepository.addAccount(login, password)
        retailPoints.getRetailPoints()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.showSelectScreen()
            }, {
                retailPointRepository.removeAccount()
                viewState.showError()
            })
    }
}