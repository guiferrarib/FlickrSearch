package com.ia.flickrsearch.photo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlickrItem(
    @SerialName("title")
    val title: String,
    @SerialName("link")
    val link: String,
    @SerialName("media")
    val media: Media,
    @SerialName("date_taken")
    val dateTaken: String,
    @SerialName("description")
    val description: String,
    @SerialName("published")
    val published: String,
    @SerialName("author")
    val author: String,
    @SerialName("author_id")
    val authorId: String,
    @SerialName("tags")
    val tags: String
)
