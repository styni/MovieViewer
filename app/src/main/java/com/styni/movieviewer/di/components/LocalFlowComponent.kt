package com.styni.movieviewer.di.components

import com.styni.movieviewer.di.modules.LocalFlowModule
import com.styni.movieviewer.di.PerFlow
import com.styni.movieviewer.presentation.detail.FilmDetailPresenter
import com.styni.movieviewer.presentation.main.FilmListPresenter
import com.styni.movieviewer.presentation.main.MainFlowPresenter
import dagger.Subcomponent

@PerFlow
@Subcomponent(modules = [(LocalFlowModule::class)])
interface LocalFlowComponent {
    fun inject(mainFlowPresenter: MainFlowPresenter)
    fun inject(filmListPresenter: FilmListPresenter)
    fun inject(filmDetailPresenter: FilmDetailPresenter)
}