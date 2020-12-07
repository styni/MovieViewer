package com.styni.movieviewer.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.styni.movieviewer.data.films_list.FilmsListPagingSource
import com.styni.movieviewer.data.search.SearchPagingSource
import com.styni.movieviewer.model.RezkaFilmDetail
import com.styni.movieviewer.model.RezkaItem
import com.styni.movieviewer.network.RezkaApi
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmsListPagingRepository @Inject constructor(private val api: RezkaApi.HtmlApi):
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    fun getFilms(genre: Int?): Flowable<PagingData<RezkaItem>> {
        return Pager(
            config = PAGING_CONFIG,
            pagingSourceFactory = { FilmsListPagingSource(api, genre) }
        ).flowable.cachedIn(this)
    }

    fun search(query: String?): Flowable<PagingData<RezkaItem>> {
        return Pager(
            config = PAGING_CONFIG,
            pagingSourceFactory = { SearchPagingSource(api, query) }
        ).flowable.cachedIn(this)
    }

    fun detailFilm(url: String): Observable<RezkaFilmDetail> {
        return api.detailFilm(url)
            .subscribeOn(Schedulers.io())
            .map { parseScript(it) }
    }

    private fun parseScript(item: RezkaFilmDetail): RezkaFilmDetail {
        val hashMap: HashMap<String, String> = hashMapOf()

        item.scripts[10].data().split("streams\":\"", "\",")[2].split(",").forEach{
            val list = it.split(" or ")
            val quality = list[0].split("]")[0].substring(1)
            val link1 = list[0].split("]")[1]
            val link2 = list[1]

            hashMap["$quality (HLS)"] = link1.replace("\\", "")
            hashMap["$quality (MP4)"] = link2.replace("\\", "")
        }

        item.videos = hashMap

        return item
    }

    companion object {
        private val PAGING_CONFIG = PagingConfig(
            pageSize = 10,
            enablePlaceholders = true,
            maxSize = 30,
            prefetchDistance = 5,
            initialLoadSize = 40)
    }
}