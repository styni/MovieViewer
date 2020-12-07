package com.styni.movieviewer.di.modules

import com.styni.movieviewer.di.PerFlow
import com.styni.movieviewer.navigation.FlowRouter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

@Module
class LocalFlowModule() {
    @Provides
    @PerFlow
    fun provideFlowCicerone(globalRouter: Router): Cicerone<FlowRouter> = Cicerone.create(FlowRouter(globalRouter))

    @Provides
    @PerFlow
    fun provideFlowNavigatorHolder(cicerone: Cicerone<FlowRouter>): NavigatorHolder = cicerone.navigatorHolder

    @Provides
    @PerFlow
    fun provideFlowRouter(cicerone: Cicerone<FlowRouter>): FlowRouter = cicerone.router
}