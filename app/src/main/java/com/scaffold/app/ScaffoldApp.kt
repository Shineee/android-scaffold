package com.scaffold.app

import android.app.Application
import com.scaffold.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ScaffoldApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ScaffoldApp)
            modules(appModule)
        }
    }
}
