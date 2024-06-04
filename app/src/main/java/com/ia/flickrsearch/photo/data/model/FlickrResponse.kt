package com.ia.flickrsearch.photo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlickrResponse(
    @SerialName("title")
    val title: String,
    @SerialName("link")
    val link: String,
    @SerialName("description")
    val description: String,
    @SerialName("modified")
    val modified: String,
    @SerialName("generator")
    val generator: String,
    @SerialName("items")
    val items: List<FlickrItem>
)
