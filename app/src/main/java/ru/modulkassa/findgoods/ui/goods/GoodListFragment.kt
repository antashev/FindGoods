package ru.modulkassa.findgoods.ui.goods

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.modulkassa.findgoods.R
import ru.modulkassa.findgoods.di.DI
import ru.modulkassa.findgoods.domain.good.GoodItem
import ru.modulkassa.findgoods.ui.shared.BaseFragment
import toothpick.Toothpick

class GoodListFragment: BaseFragment(), GoodListView {
    @InjectPresenter
    lateinit var presenter: GoodListPresenter

    @ProvidePresenter
    fun providePresenter(): GoodListPresenter {
        return Toothpick.openScope(DI.MAIN_ACTIVITY).getInstance(GoodListPresenter::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_good_list, container, false)
    }

    override fun showError(throwable: Throwable) {
        TODO("not implemented")
    }

    override fun updateItems(items: List<GoodItem>) {
        TODO("not implemented")
    }
}