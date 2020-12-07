package com.styni.movieviewer.presentation.main

import com.styni.movieviewer.di.App
import com.styni.movieviewer.navigation.FlowRouter
import com.styni.movieviewer.util.Screens
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

class MainFlowPresenter : MvpPresenter<MainView>() {

    @Inject
    lateinit var router: FlowRouter

    init {
        App.instance.flowComponent?.inject(this)
    }

    fun onBackPressed() {
        router.finishFlow()
    }

    fun search(query: String) {
        router.startFlow(Screens.FilmListQuery(query))
    }

}