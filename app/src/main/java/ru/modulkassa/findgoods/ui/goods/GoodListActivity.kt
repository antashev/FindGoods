package ru.modulkassa.findgoods.ui.goods

import android.os.Bundle
import android.view.Menu
import ru.modulkassa.findgoods.di.DI
import ru.modulkassa.findgoods.domain.good.di.GoodItemSyncModule
import ru.modulkassa.findgoods.ui.shared.BaseActivity
import toothpick.Toothpick

class GoodListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.openScopes(DI.APP_SCOPE, DI.MAIN_ACTIVITY).apply {
            installModules(GoodItemSyncModule())
        }

        super.onCreate(savedInstanceState)
        showFragment(GoodListFragment(), android.R.id.content)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(isFinishing) {
            Toothpick.closeScope(DI.MAIN_ACTIVITY)
        }
    }
}
