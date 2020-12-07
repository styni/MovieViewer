package com.styni.movieviewer.model

import com.google.gson.annotations.SerializedName

data class RezkaSeriesRequest(

    @SerializedName("message")
    val message: String,

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("url")
    val url: String
)
