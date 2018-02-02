package ru.modulkassa.findgoods.ui.goods

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.OnScrollListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_good_list.goodsList
import kotlinx.android.synthetic.main.fragment_good_list.swipeContainer
import ru.modulkassa.findgoods.R
import ru.modulkassa.findgoods.di.DI
import ru.modulkassa.findgoods.domain.good.Good
import ru.modulkassa.findgoods.ui.shared.BaseFragment
import timber.log.Timber
import toothpick.Toothpick

class GoodListFragment: BaseFragment(), GoodListView {
    @InjectPresenter
    lateinit var presenter: GoodListPresenter
    lateinit var adapter: GoodListAdapter

    @ProvidePresenter
    fun providePresenter(): GoodListPresenter {
        return Toothpick.openScope(DI.MAIN_ACTIVITY).getInstance(GoodListPresenter::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_good_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GoodListAdapter(ArrayList(), { Timber.i("hello")})
        goodsList.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        goodsList.layoutManager = layoutManager
        goodsList.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        swipeContainer.setOnRefreshListener {
            presenter.refreshItems()
        }
        goodsList.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if(!presenter.needDownloadMore(totalItemCount)) {
                    return
                }
                if (!adapter.loading && totalItemCount <= (lastVisibleItem + 4)) {
                    presenter.downloadNext()
                    adapter.loading = true
                }
            }
        })
    }

    override fun addItems(items: List<Good>) {
        adapter.addItems(items)
    }

    override fun hideLoadMoreProgress() {
        adapter.loading = false
    }

    override fun showProgress() {
        TODO("not implemented")
    }

    override fun hideSwipeProgress() {
        swipeContainer.isRefreshing = false
    }

    override fun showError(throwable: Throwable) {
        TODO("not implemented")
    }

    override fun updateItems(items: List<Good>) {
        adapter.updateItems(items)
    }
}