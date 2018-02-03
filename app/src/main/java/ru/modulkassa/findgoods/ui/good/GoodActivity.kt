package ru.modulkassa.findgoods.ui.good

import android.content.Context
import android.content.Intent
import android.os.Bundle
import ru.modulkassa.findgoods.ui.shared.BaseActivity

class GoodActivity : BaseActivity() {

    companion object {
        const val GOOD_EXTRA = "good extra"
        const val NEW_GOOD_EXTRA = "new good extra"
        const val SUCCESS = 1

        fun create(context: Context?, good: String): Intent {
            val intent = Intent(context, GoodActivity::class.java)
            intent.putExtra(GOOD_EXTRA, good)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var barcode = ""
        if (intent.hasExtra(GOOD_EXTRA)) {
            barcode = intent.getStringExtra(GOOD_EXTRA)
        }
        showFragment(GoodFragment.createFragment(barcode), android.R.id.content)
    }

    fun finishWithResult(goodJson: String) {
        val intent = Intent()
        intent.putExtra(NEW_GOOD_EXTRA, goodJson)
        setResult(SUCCESS, intent)
        finish()
    }
}