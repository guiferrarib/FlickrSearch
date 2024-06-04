package com.ia.flickrsearch.domain

/**
 * Copyright (c) 2024 AngelLira
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
import com.ia.flickrsearch.data.model.FlickrItem
import com.ia.flickrsearch.data.model.FlickrPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Locale

class GetPhotosUseCase(private val repository: PhotoRepository) {

    fun execute(query: String): Flow<List<FlickrPhoto>> = flow {
        val flickrItems = repository.fetchPhotos(query)
        val formattedItems = flickrItems.map { it.toFlickrPhoto() }
        emit(formattedItems)
    }

    private fun FlickrItem.toFlickrPhoto(): FlickrPhoto {
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
}