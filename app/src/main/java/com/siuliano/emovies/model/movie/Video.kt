package com.siuliano.emovies.model.movie

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Videos(
    val results: List<Video>
)

@JsonClass(generateAdapter = true)
data class Video(
    val key: String,
    val site: String,
    val type: String,
    val official: Boolean
)