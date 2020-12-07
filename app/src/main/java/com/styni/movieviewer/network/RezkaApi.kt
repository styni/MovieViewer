package com.styni.movieviewer.network

import com.styni.movieviewer.model.RezkaFilmDetail
import com.styni.movieviewer.model.RezkaList
import com.styni.movieviewer.model.RezkaSeriesRequest
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

interface RezkaApi {

    interface HtmlApi {
        @GET("page/{page}/?filter=watching")
        fun getFilms(@Path(value="page", encoded = true) page: Int, @Query("genre") genre: Int?) : Single<RezkaList>

        @GET("{category}/best/page/{page}/")
        fun best(@Path(value = "category", encoded = true) category: String, @Path(value="page", encoded = true) page: Int) : Observable<RezkaList?>

        @GET("index.php?do=search&subaction=search")
        fun search(@Query("q") query: String?, @Query("page") page: Int): Single<RezkaList>

        @GET("{path}")
        fun detailFilm(@Path(value = "path", encoded = true) path: String): Observable<RezkaFilmDetail>
    }

    interface JsonApi {
        // JSON
//    @POST("engine/ajax/gettrailervideo.php")
//    fun getTrailer(id: String): Single<TrailerEntity>

//    @POST("ajax/get_cdn_series")
//    fun getMovie(): Observable<>

        @POST("ajax/get_cdn_series")
        fun getStream(
            @Query("id") id: String,
            @Query("translator_id") translatorId: String,
            @Query("season") season: String,
            @Query("episode") episode: String,
            @Query("action") action: String = "get_stream"
        ): Single<RezkaSeriesRequest>
    }

    companion object {
        const val BACK_URL = "https://rezka.ag"
    }
}