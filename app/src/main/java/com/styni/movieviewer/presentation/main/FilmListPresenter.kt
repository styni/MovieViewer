package com.styni.movieviewer.presentation.main

import com.styni.movieviewer.data.FilmsListPagingRepository
import com.styni.movieviewer.di.App
import com.styni.movieviewer.model.RezkaItem
import com.styni.movieviewer.navigation.FlowRouter
import com.styni.movieviewer.util.Screens
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter
import javax.inject.Inject

class FilmListPresenter : MvpPresenter<FilmListView>() {

    @Inject
    lateinit var repository: FilmsListPagingRepository

    @Inject
    lateinit var router: FlowRouter

    private val disposable = CompositeDisposable()

    init {
        App.instance.flowComponent?.inject(this)
    }

    fun getFilms(genre: Int?) {
        disposable.add(
            repository.getFilms(genre)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.setData(it)
                }, {
                    viewState.showError()
                })
        )
    }

    fun search(query: String?) {
        disposable.add(
            repository.search(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.setData(it)
                }, {
                    viewState.showError()
                })
        )
    }

    fun detailFilm(item: RezkaItem) {
        router.startFlow(Screens.DetailFilm(item))
    }

    fun onBackPressed() {
        router.finishFlow()
    }

    override fun onDestroy() {
        disposable.clear()
    }
}