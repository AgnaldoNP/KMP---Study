package dev.agnaldo.kmpsample.ui

import android.app.Application
import dev.agnaldo.kmpsample.mobile.di.Modules.appComposeModule
import dev.agnaldo.kmpsample.shared.di.Modules.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(sharedModule, appComposeModule)
        }
    }
}
