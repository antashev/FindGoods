package ru.modulkassa.findgoods

import android.app.Application
import ru.modulkassa.findgoods.di.DI
import ru.modulkassa.findgoods.di.AppModule
import toothpick.Toothpick
import timber.log.Timber.DebugTree
import timber.log.Timber



class App: Application() {
    override fun onCreate() {
        super.onCreate()

        // todo install tree for release type
        Timber.plant(DebugTree())

        val appScope = Toothpick.openScope(DI.APP_SCOPE)
        appScope.installModules(AppModule(this))
    }
}