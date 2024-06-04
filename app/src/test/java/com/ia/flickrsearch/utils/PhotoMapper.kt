package com.ia.flickrsearch.utils

import com.ia.flickrsearch.photo.data.model.FlickrItem
import com.ia.flickrsearch.photo.data.model.FlickrPhoto
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Copyright (c) 2024 AngelLira
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
fun FlickrItem.toFlickrPhoto(): FlickrPhoto {
    return FlickrPhoto(
        title = title,
        link = link,
        mediaUrl = media.m.replace("_m", "_b"),
        formattedDateTaken = formatDate(dateTaken),
        description = formatDescription(description),
        formattedPublishedDate = formatDate(published),
        author = extractAuthor(author),
        tags = tags.split(" ")
    )
}

private fun formatDate(dateStr: String): String {
    return try {
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(dateStr)
        SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(date!!)
    } catch (e: Exception) {
        dateStr
    }
}

private fun formatDescription(description: String): String {
    return description.replace(Regex("<[^>]*>"), "").trim()
}

private fun extractAuthor(author: String): String {
    return author.substringAfter("(\"").substringBefore("\")")
}