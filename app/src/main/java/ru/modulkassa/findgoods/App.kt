package ru.modulkassa.findgoods

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import ru.modulkassa.findgoods.di.DI
import ru.modulkassa.findgoods.di.AppModule
import toothpick.Toothpick
import timber.log.Timber.DebugTree
import timber.log.Timber



class App: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.USE_CRASHLYTICS) {
            Fabric.with(this, Crashlytics())
        }
        // todo install tree for release type
        Timber.plant(DebugTree())

        val appScope = Toothpick.openScope(DI.APP_SCOPE)
        appScope.installModules(AppModule(this))
    }
}