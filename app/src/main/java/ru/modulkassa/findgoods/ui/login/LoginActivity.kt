package ru.modulkassa.findgoods.ui.login

import android.os.Bundle
import ru.modulkassa.findgoods.di.DI
import ru.modulkassa.findgoods.domain.repository.RetailPointRepository
import ru.modulkassa.findgoods.ui.login.points.PointsFragment
import ru.modulkassa.findgoods.ui.shared.BaseActivity
import toothpick.Toothpick
import javax.inject.Inject

class LoginActivity : BaseActivity() {
    @Inject
    lateinit var retailPointRepo: RetailPointRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        val scope = Toothpick.openScope(DI.APP_SCOPE)
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
        if (retailPointRepo.hasAccount()) {
            showSelectPointView()
        } else {
            showFragment(LoginFragment(), android.R.id.content)
        }
    }

    fun showSelectPointView() {
        showFragment(PointsFragment(), android.R.id.content)
    }
}