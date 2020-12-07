package com.styni.movieviewer.di

import android.app.Application
import com.styni.movieviewer.di.components.AppComponent
import com.styni.movieviewer.di.components.DaggerAppComponent
import com.styni.movieviewer.di.components.LocalFlowComponent
import com.styni.movieviewer.di.modules.AppModule
import com.styni.movieviewer.di.modules.LocalFlowModule
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class App : Application() {
    var appComponent: AppComponent? = null
        get() {
            if (field == null) {
                field = DaggerAppComponent.builder()
                    .appModule(AppModule(this))
                    .build()
            }
            return field
        }

    var flowComponent: LocalFlowComponent? = null
        get() {
            if (field == null) {
                field = appComponent?.flowComponent(LocalFlowModule())
            }
            return field
        }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
    }
}