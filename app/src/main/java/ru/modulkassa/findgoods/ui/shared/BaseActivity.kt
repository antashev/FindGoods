package ru.modulkassa.findgoods.ui.shared

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity

open class BaseActivity: MvpAppCompatActivity() {

    protected fun showFragment(fragment: Fragment, @IdRes contentId: Int) {
        val transaction = supportFragmentManager?.beginTransaction()
        transaction?.replace(contentId, fragment)
        transaction?.commit()
    }
}