package ru.modulkassa.findgoods.ui.login

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_login.login
import kotlinx.android.synthetic.main.fragment_login.password
import kotlinx.android.synthetic.main.fragment_login.root
import kotlinx.android.synthetic.main.fragment_login.signIn
import ru.modulkassa.findgoods.R
import ru.modulkassa.findgoods.di.DI
import ru.modulkassa.findgoods.ui.shared.BaseFragment
import toothpick.Toothpick

class LoginFragment : BaseFragment(), LoginView {

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter(): LoginPresenter {
        return Toothpick.openScope(DI.APP_SCOPE).getInstance(LoginPresenter::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signIn.setOnClickListener {
            presenter.singIn(login.text.toString(), password.text.toString())
        }
    }

    override fun showError() {
        Snackbar.make(root, getText(R.string.error_cant_login),
            Snackbar.LENGTH_LONG).show()
    }

    override fun showSelectScreen() {
        (activity as LoginActivity).showSelectPointView()
    }
}