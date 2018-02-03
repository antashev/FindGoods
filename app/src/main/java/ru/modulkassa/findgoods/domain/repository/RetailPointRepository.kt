package ru.modulkassa.findgoods.domain.repository

import android.content.Context

class RetailPointRepository(
    context: Context
) {
    companion object {
        private const val HAS_ACCOUNT = "has_account"
        private const val PASSWORD = "password"
        private const val LOGIN = "login"
        private const val SELECTED_POINT = "selected_point"
    }

    private val sharedPref = context.getSharedPreferences("retail_point_repo", Context.MODE_PRIVATE)

    fun hasAccount(): Boolean {
        return sharedPref.getBoolean(HAS_ACCOUNT, false)
    }

    fun removeAccount() {
        sharedPref
            .edit()
            .putBoolean(HAS_ACCOUNT, false)
            .apply()
    }

    fun addAccount(login: String, password: String) {
        sharedPref
            .edit()
            .putBoolean(HAS_ACCOUNT, true)
            .putString(LOGIN, login)
            .putString(PASSWORD, password)
            .apply()
    }

    fun login(): String = sharedPref.getString(LOGIN, "")

    fun password(): String = sharedPref.getString(PASSWORD, "")

    fun selectedPointId(): String = sharedPref.getString(SELECTED_POINT, "")

    fun setSelectedPointId(id: String) {
        sharedPref
            .edit()
            .putString(SELECTED_POINT, id)
            .apply()
    }
}