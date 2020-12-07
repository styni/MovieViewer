package com.styni.movieviewer.presentation.detail

import com.styni.movieviewer.model.RezkaFilmDetail
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface FilmDetailView : MvpView {
    fun setData(data: RezkaFilmDetail)
}