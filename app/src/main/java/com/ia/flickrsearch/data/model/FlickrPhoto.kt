package com.ia.flickrsearch.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FlickrPhoto(
    val title: String,
    val link: String,
    val mediaUrl: String,
    val formattedDateTaken: String,
    val description: String,
    val formattedPublishedDate: String,
    val author: String,
    val tags: List<String>
)
