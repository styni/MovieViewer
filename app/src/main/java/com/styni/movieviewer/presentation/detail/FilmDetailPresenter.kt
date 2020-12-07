package com.styni.movieviewer.presentation.detail

import com.styni.movieviewer.data.FilmsListPagingRepository
import com.styni.movieviewer.di.App
import com.styni.movieviewer.model.RezkaItem
import com.styni.movieviewer.navigation.FlowRouter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter
import javax.inject.Inject

class FilmDetailPresenter : MvpPresenter<FilmDetailView>() {

    private lateinit var quickInfo: RezkaItem
    private val disposable = CompositeDisposable()

    @Inject
    lateinit var repository: FilmsListPagingRepository

    @Inject
    lateinit var router: FlowRouter

    init {
        App.instance.flowComponent?.inject(this)
    }

    fun detailFilm(quickInfo: RezkaItem) {
        this.quickInfo = quickInfo
        disposable.add(
            repository.detailFilm(this.quickInfo.url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.setData(it)
                }, {

                })
        )
    }

    fun onBaskPressed() {
        router.finishFlow()
    }


}