package com.ia.flickrsearch.utils.di

import com.ia.flickrsearch.utils.MockClient
import com.ia.flickrsearch.utils.testKtorClient
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Copyright (c) 2024
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
val testPlatformModule: Module = module {
    single<MockClient> { MockClient() }
    single<HttpClient> { testKtorClient(get()) }
}