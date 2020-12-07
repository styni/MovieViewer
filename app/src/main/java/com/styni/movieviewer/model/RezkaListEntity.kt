package com.styni.movieviewer.model

import pl.droidsonroids.jspoon.annotation.Selector

class RezkaList {
    @Selector(".b-content__inline_item")
    lateinit var list: ArrayList<RezkaItem>
}

class RezkaItem {
    /** URL элемента **/
    @Selector(".b-content__inline_item", attr = "data-url")
    lateinit var url: String

    /** URL изображения **/
    @Selector(".b-content__inline_item-cover > a > img", attr = "src")
    lateinit var image: String

    /** Название **/
    @Selector(value = ".b-content__inline_item-link > a")
    lateinit var title: String

    /** Тип элемента **/
    @Selector(value = ".b-content__inline_item-cover > a > span > i.entity")
    lateinit var type: String

    /** Год, Страна, Жанр **/
    @Selector(value = ".b-content__inline_item-link > div")
    lateinit var info: String

    /** Кол-во сезонов, серий **/
    @Selector(value = ".b-content__inline_item-cover > a > span.info")
    lateinit var series_info: String

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RezkaItem

        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        return url.hashCode()
    }
}