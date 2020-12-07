package com.styni.movieviewer.model

import org.jsoup.nodes.Element
import pl.droidsonroids.jspoon.annotation.Selector
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class RezkaFilmDetail {
    /** Id **/
    @Selector(value = "a.b-sidelinks__link.show-trailer", attr = "data-id")
    lateinit var id: String

    /** Название **/
    @Selector(value = ".b-post__title > h1")
    lateinit var title: String

    /** Название в оригинале **/
    @Selector(value = ".b-post__origtitle")
    var originalTitle: String? = null

    /** Постер к фильму **/
    @Selector(value = ".b-sidecover > a > img", attr = "src")
    lateinit var image: String

    /** IMDb рейтинг **/
    @Selector(value = ".b-post__info_rates.imdb > span")
    var imdbRating: String? = null

    /** Кинопоиск рейтинг **/
    @Selector(value = ".b-post__info_rates.kp > span")
    var kpRating: String? = null

//    /** Дата выхода **/
//    @Selector(value = "td.l > h2")
//    lateinit var dateRelease: String

    /** Описание **/
    @Selector(value = ".b-post__description_text")
    lateinit var description: String

    /** Режиссер **/
    @Selector(value = "div.persons-list-holder > span.item > span.person-name-item > a > span")
    lateinit var director: String

    /** Актеры **/
    @Selector(value = "div.persons-list-holder > span.item > span > a > span")
    lateinit var actors: ArrayList<String>

    /** Жанр **/
    @Selector(value = "span[itemprop=\"genre\"]")
    lateinit var genre: ArrayList<String>

    /** Продолжительность **/
    @Selector(value = "td[itemprop=\"duration\"]")
    lateinit var duration: String

    /** Скрипты на странице **/
    @Selector(value = "script")
    lateinit var scripts: ArrayList<Element>

    /** Список озвучек **/
    @Selector(value = "#translators-list > li")
    lateinit var translatorsList: ArrayList<TranslatorEntity>

    /** Список сезонов **/
    @Selector(value = "#simple-seasons-tabs > li")
    lateinit var seasonsList: ArrayList<String>

    /** Список серий по сезонам **/
    @Selector(value = "#simple-episodes-tabs > ul")
    lateinit var seriesListBySeason:ArrayList<SeriesListEntity>

    /** Список ссылок на видео (качество - ссылка) **/
    lateinit var videos: HashMap<String, String>
}

class TranslatorEntity {
    @Selector(value = "li", attr = "data-translator_id")
    lateinit var id: String

    @Selector(value = "li")
    lateinit var name: String
}

class SeriesListEntity {
    @Selector(value = "li")
    lateinit var seriesList: ArrayList<SeriesEntity>
}

class SeriesEntity {
    @Selector(value = "li", attr = "data-id")
    lateinit var filmId: String

    @Selector(value = "li", attr = "data-season_id")
    lateinit var seasonId: String

    @Selector(value = "li", attr = "data-episode_id")
    lateinit var episodeId: String

    @Selector(value = "li")
    lateinit var name: String
}