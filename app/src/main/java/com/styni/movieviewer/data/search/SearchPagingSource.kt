package com.styni.movieviewer.data.search

import androidx.paging.rxjava2.RxPagingSource
import com.styni.movieviewer.model.RezkaItem
import com.styni.movieviewer.network.RezkaApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class SearchPagingSource(private val api: RezkaApi.HtmlApi, private val query: String?): RxPagingSource<Int, RezkaItem>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, RezkaItem>> {
        val page = params.key ?: 1

        return api.search(query, page)
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