package ru.modulkassa.findgoods

import android.app.Application
import ru.modulkassa.findgoods.di.DI
import ru.modulkassa.findgoods.di.AppModule
import toothpick.Toothpick

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        val appScope = Toothpick.openScope(DI.APP_SCOPE)
        appScope.installModules(AppModule(this))
    }
}