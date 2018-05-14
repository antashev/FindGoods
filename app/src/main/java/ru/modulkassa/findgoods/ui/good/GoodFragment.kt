package ru.modulkassa.findgoods.ui.good

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_good_detail.barcodeTitle
import kotlinx.android.synthetic.main.fragment_good_detail.contentLayout
import kotlinx.android.synthetic.main.fragment_good_detail.minPrice
import kotlinx.android.synthetic.main.fragment_good_detail.name
import kotlinx.android.synthetic.main.fragment_good_detail.price
import kotlinx.android.synthetic.main.fragment_good_detail.progressBar
import kotlinx.android.synthetic.main.fragment_good_detail.root
import kotlinx.android.synthetic.main.fragment_good_detail.save
import kotlinx.android.synthetic.main.fragment_good_detail.toolbar
import ru.modulkassa.findgoods.R
import ru.modulkassa.findgoods.di.DI
import ru.modulkassa.findgoods.domain.good.Good
import ru.modulkassa.findgoods.ui.good.GoodActivity.Companion.GOOD_EXTRA
import ru.modulkassa.findgoods.ui.shared.BaseFragment
import ru.modulkassa.findgoods.ui.shared.DecimalDigitsInputFilter
import ru.modulkassa.findgoods.ui.shared.toBigDecimal
import ru.modulkassa.findgoods.ui.shared.toCurrencyString
import toothpick.Toothpick
import java.math.BigDecimal
import javax.inject.Inject

class GoodFragment : BaseFragment(), GoodDetailView {

    companion object {
        fun createFragment(good: String): GoodFragment {
            val arguments = Bundle()
            arguments.putString(GOOD_EXTRA, good)
            val goodFragment = GoodFragment()
            goodFragment.arguments = arguments
            return goodFragment
        }
    }

    @InjectPresenter
    lateinit var presenter: GoodDetailPresenter

    @ProvidePresenter
    fun providePresenter(): GoodDetailPresenter {
        return Toothpick.openScope(DI.GOOD_SCOPE).getInstance(GoodDetailPresenter::class.java)
    }

    @Inject
    lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        val scope = Toothpick.openScope(DI.APP_SCOPE)
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_good_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.navigationIcon = ContextCompat.getDrawable(context!!,
            R.drawable.ic_close_white_24dp)
        toolbar.setNavigationOnClickListener {
            activity?.finish()
        }
        val goodJson = arguments?.getString(GOOD_EXTRA)
        if (goodJson != null) {
            val good = gson.fromJson(goodJson, Good::class.java)
            val barcode = good.barcode
            barcodeTitle.text = getString(R.string.barcode, barcode)
            name.setText(good.name)
            price.hint = (good.price ?: BigDecimal.ZERO).toCurrencyString()
            minPrice.hint = (good.minPrice ?: BigDecimal.ZERO).toCurrencyString()
            val filters = arrayOf(DecimalDigitsInputFilter(9, 2))
            price.filters = filters
            minPrice.filters = filters

            save.setOnClickListener {
                if (verify()) {
                    val newGood = good.copy(
                        name = name.text.toString(),
                        barcode = barcode,
                        price = getCorrectPrice(price),
                        minPrice = getCorrectPrice(minPrice)
                    )
                    presenter.updateGood(newGood)
                }
            }
        }
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
        contentLayout.visibility = View.GONE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
        contentLayout.visibility = View.VISIBLE
    }

    override fun goBack(good: Good) {
        (activity as GoodActivity).finishWithResult(gson.toJson(good))
    }

    override fun showError(throwable: Throwable) {
        Snackbar.make(root, getString(R.string.error_cant_upload), Snackbar.LENGTH_LONG).show()
    }

    private fun verify(): Boolean {
        var result = true
        if (name.text.isBlank()) {
            result = false
            name.error = "Поле не может быть пустым"
        }

        if (getCorrectPrice(price) < getCorrectPrice(minPrice)) {
            result = false
            minPrice.error = "Минимальная цена должна быть меньше цены"
        }
        return result
    }

    private fun getCorrectPrice(editText: EditText): BigDecimal {
        if (editText.text.isBlank()) {
            return editText.hint.toString().toBigDecimal()
        }
        return editText.text.toString().toBigDecimal()
    }

}