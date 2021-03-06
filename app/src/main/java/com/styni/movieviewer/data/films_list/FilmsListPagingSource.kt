package com.styni.movieviewer.data.films_list

import androidx.paging.rxjava2.RxPagingSource
import com.styni.movieviewer.model.RezkaItem
import com.styni.movieviewer.network.RezkaApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class FilmsListPagingSource (private val api: RezkaApi.HtmlApi, private val genre: Int?): RxPagingSource<Int, RezkaItem>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, RezkaItem>> {
        val page = params.key ?: 1

        return api.getFilms(page, genre)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it.list, page) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: List<RezkaItem>, position: Int): LoadResult<Int, RezkaItem> {
        return LoadResult.Page(
            data = data,
            prevKey = if (position == 1) null else position - 1,
            nextKey = position + 1
        )
    }
}