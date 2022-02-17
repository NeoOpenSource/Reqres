package com.example.reqres

import android.app.Application
import com.example.reqres.module.diModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(diModules)
        }
    }
}