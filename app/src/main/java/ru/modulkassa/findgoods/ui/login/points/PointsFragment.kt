package ru.modulkassa.findgoods.ui.login.points

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_points.emptyList
import kotlinx.android.synthetic.main.fragment_points.pointsList
import kotlinx.android.synthetic.main.fragment_points.progressBar
import kotlinx.android.synthetic.main.fragment_points.root
import kotlinx.android.synthetic.main.fragment_points.toolbar
import ru.modulkassa.findgoods.R
import ru.modulkassa.findgoods.di.DI
import ru.modulkassa.findgoods.domain.point.RetailPoint
import ru.modulkassa.findgoods.ui.goods.GoodListActivity
import ru.modulkassa.findgoods.ui.shared.BaseFragment
import toothpick.Toothpick

class PointsFragment : BaseFragment(), PointsView {

    @InjectPresenter
    lateinit var presenter: PointsPresenter

    @ProvidePresenter
    fun providePresenter(): PointsPresenter {
        return Toothpick.openScope(DI.APP_SCOPE).getInstance(PointsPresenter::class.java)
    }

    lateinit var adapter: PointsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        val scope = Toothpick.openScope(DI.APP_SCOPE)
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_points, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PointsAdapter(ArrayList()) { point ->
            presenter.saveSelected(point)
        }
        pointsList.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        pointsList.layoutManager = layoutManager
        pointsList.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))

        toolbar.inflateMenu(R.menu.menu_points)
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sync_points -> {
                    presenter.updatePoints()
                }
                else -> return@setOnMenuItemClickListener false
            }
            true
        }
    }

    override fun gotoGoodsScreen(finish: Boolean) {
        startActivity(Intent(context, GoodListActivity::class.java))
        if (finish) {
            activity?.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.updatePoints()
    }

    override fun updateSelection(points: List<RetailPoint>) {
        if (points.isNotEmpty()) {
            emptyList.visibility = View.GONE
            pointsList.visibility = View.VISIBLE
            adapter.updateItems(points)
        }
    }

    override fun showError() {
        Snackbar.make(root, getText(R.string.error_cant_download_points),
            Snackbar.LENGTH_LONG).show()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
        pointsList.visibility = View.GONE
        emptyList.visibility = View.GONE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
        if (adapter.itemCount > 0) {
            pointsList.visibility = View.VISIBLE
        } else {
            emptyList.visibility = View.VISIBLE
        }
    }
}