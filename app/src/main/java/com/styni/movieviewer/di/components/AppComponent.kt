package com.styni.movieviewer.di.components

import com.styni.movieviewer.di.modules.AppModule
import com.styni.movieviewer.di.modules.GlobalFlowModule
import com.styni.movieviewer.di.modules.LocalFlowModule
import com.styni.movieviewer.ui.global.AppActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, GlobalFlowModule::class])
interface AppComponent {
    fun inject(appActivity: AppActivity)
    fun flowComponent(module: LocalFlowModule): LocalFlowComponent
}