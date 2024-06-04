package com.ia.flickrsearch.utils.di


import com.ia.flickrsearch.photo.presentation.di.appModule
import com.ia.flickrsearch.app.di.networkModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.core.module.plus

/**
 * Copyright (c) 2024
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
fun startTestKoin(testModule: Module): KoinApplication {
    return startKoin {
        listOf(testModule)
            .plus(appModule)
            .plus(networkModule)
    }
}

fun stopTestKoin() {
    stopKoin()
}