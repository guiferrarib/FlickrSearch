package com.ia.flickrsearch.presentation.di

import android.annotation.SuppressLint
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

/**
 * Copyright (c) 2024
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
val networkModule = module {

    single<HttpClient> {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            engine {
                https {
                    trustManager = TrustAllCertificates()
                }
            }
        }
    }
}

@SuppressLint("CustomX509TrustManager")
class TrustAllCertificates : javax.net.ssl.X509TrustManager {
    @SuppressLint("TrustAllX509TrustManager")
    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>?, authType: String?) {}
    @SuppressLint("TrustAllX509TrustManager")
    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>?, authType: String?) {}
    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = arrayOf()
}