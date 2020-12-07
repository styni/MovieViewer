package com.styni.movieviewer.presentation.main

import androidx.paging.PagingData
import com.styni.movieviewer.model.RezkaItem
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface FilmListView : MvpView {
    fun setData(data: PagingData<RezkaItem>)
    fun showError()
}