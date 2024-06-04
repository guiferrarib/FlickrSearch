package com.ia.flickrsearch.utils

import com.ia.flickrsearch.utils.MockResponse
import io.ktor.client.request.HttpRequestData

/**
 * Copyright (c) 2024
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
fun interface ResponseInterceptor {
    operator fun invoke(request: HttpRequestData): MockResponse
}