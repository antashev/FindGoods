package ru.modulkassa.findgoods.ui.goods

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.OnScrollListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_good_list.emptyList
import kotlinx.android.synthetic.main.fragment_good_list.goodsList
import kotlinx.android.synthetic.main.fragment_good_list.progressBar
import kotlinx.android.synthetic.main.fragment_good_list.root
import kotlinx.android.synthetic.main.fragment_good_list.swipeContainer
import kotlinx.android.synthetic.main.fragment_good_list.toolbar
import ru.modulkassa.findgoods.R
import ru.modulkassa.findgoods.di.DI
import ru.modulkassa.findgoods.domain.good.Good
import ru.modulkassa.findgoods.ui.good.GoodActivity
import ru.modulkassa.findgoods.ui.scan.ScanActivity
import ru.modulkassa.findgoods.ui.shared.BaseFragment
import toothpick.Toothpick
import javax.inject.Inject

class GoodListFragment: BaseFragment(), GoodListView {
    companion object {
        const val SCAN_REQUEST =  1
        const val MODIFY_GOOD_ITEM = 2
    }

    @Inject
    lateinit var gson: Gson
    @InjectPresenter
    lateinit var presenter: GoodListPresenter
    lateinit var adapter: GoodListAdapter

    @ProvidePresenter
    fun providePresenter(): GoodListPresenter {
        return Toothpick.openScope(DI.GOOD_SCOPE).getInstance(GoodListPresenter::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val scope = Toothpick.openScope(DI.APP_SCOPE)
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_good_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GoodListAdapter(ArrayList()) { good ->
            presenter.showDetail(good)
        }
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

        toolbar.inflateMenu(R.menu.menu_goods_list)
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.scan_barcode -> {
                    startActivityForResult(Intent(context, ScanActivity::class.java), SCAN_REQUEST)
                }
                else -> return@setOnMenuItemClickListener false
            }
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SCAN_REQUEST && resultCode == ScanActivity.SUCCESS) {
            val barcode = data?.getStringExtra(ScanActivity.BARCODE_RESULT) ?: ""
            if (barcode.isNotBlank()) {
                presenter.findGood(barcode)
            }
        }
        if (requestCode == MODIFY_GOOD_ITEM && resultCode == GoodActivity.SUCCESS) {
            val goodJson = data?.getStringExtra(GoodActivity.NEW_GOOD_EXTRA) ?: ""
            if (goodJson.isNotBlank()) {
                val good = gson.fromJson(goodJson, Good::class.java)
                adapter.updateItem(good)
            }
        }
    }

    override fun showGoodDetail(goodJson: String) {
        startActivityForResult(GoodActivity.create(context, goodJson), MODIFY_GOOD_ITEM)
    }

    override fun addItems(items: List<Good>) {
        adapter.addItems(items)
    }

    override fun hideLoadMoreProgress() {
        adapter.loading = false
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
        goodsList.visibility = View.GONE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
        goodsList.visibility = View.VISIBLE
    }

    override fun hideSwipeProgress() {
        swipeContainer.isRefreshing = false
    }

    override fun showError(throwable: Throwable) {
        Snackbar.make(root, getText(R.string.error_cant_download_goods),
            Snackbar.LENGTH_LONG).show()
    }

    override fun showCantFindGoodError() {
        Snackbar.make(root, getText(R.string.error_cant_find_good),
            Snackbar.LENGTH_LONG).show()
    }

    override fun updateItems(items: List<Good>) {
        if (items.isEmpty()) {
            emptyList.visibility = View.VISIBLE
        } else {
            emptyList.visibility = View.GONE
        }
        adapter.updateItems(items)
    }
}