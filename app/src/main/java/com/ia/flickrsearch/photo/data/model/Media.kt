package com.ia.flickrsearch.photo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Media(
    @SerialName("m")
    val m: String
)
