package com.styni.movieviewer.util

import com.styni.movieviewer.model.RezkaItem
import com.styni.movieviewer.ui.detail.FilmDetailFragment
import com.styni.movieviewer.ui.main.FilmListFragment
import com.styni.movieviewer.ui.main.MainFlowFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object MainFlow : SupportAppScreen() {
        override fun getFragment() = MainFlowFragment()
    }

    data class FilmList(val genre: Int?) : SupportAppScreen() {
        override fun getFragment() = FilmListFragment.newInstance(genre)
    }

    data class FilmListQuery(val query: String) : SupportAppScreen() {
        override fun getFragment() = FilmListFragment.newInstance(query)
    }

    data class DetailFilm(val film: RezkaItem) : SupportAppScreen() {
        override fun getFragment() = FilmDetailFragment.newInstance(film)
    }

}