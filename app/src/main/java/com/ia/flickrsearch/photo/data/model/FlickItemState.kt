package com.ia.flickrsearch.photo.data.model

/**
 * Copyright (c) 2024 AngelLira
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
data class FlickrItemState (
    val items: List<FlickrPhoto> = listOf(),
    val loading: Boolean = false,
    val error: String? = null
)