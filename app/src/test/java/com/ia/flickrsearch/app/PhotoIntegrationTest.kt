package com.ia.flickrsearch.app

import app.cash.turbine.test
import com.ia.flickrsearch.photo.data.datasource.PhotoRemoteDataSource
import com.ia.flickrsearch.photo.data.model.FlickrItemState
import com.ia.flickrsearch.photo.data.model.FlickrResponse
import com.ia.flickrsearch.photo.presentation.di.appModule
import com.ia.flickrsearch.photo.presentation.viewmodel.PhotoViewModel
import com.ia.flickrsearch.utils.MockClient
import com.ia.flickrsearch.utils.MockResponse
import com.ia.flickrsearch.utils.di.stopTestKoin
import com.ia.flickrsearch.utils.di.testPlatformModule
import com.ia.flickrsearch.utils.toFlickrPhoto
import io.kotest.assertions.any
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Copyright (c) 2024
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
class PhotoIntegrationTest : KoinTest {
    private val dispatcherProvider = StandardTestDispatcher()

    private val jsonResponse = readJsonFile("photos.json")

    @OptIn(FlowPreview::class)
    private val viewModel: PhotoViewModel by inject()

    private val localDataSource: PhotoRemoteDataSource by inject()
    private val mockClient: MockClient by inject()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                testPlatformModule + appModule
            )
        }
        Dispatchers.setMain(dispatcherProvider)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun tearDown() {
        stopTestKoin()
        Dispatchers.resetMain() // reset Main dispatcher to the original Main dispatcher
        dispatcherProvider.cancel()
    }

    @OptIn(FlowPreview::class)
    @Test
    fun `test fetching photos updates viewmodel state`() = runTest {
        //Given
        val response = MockResponse(jsonResponse, HttpStatusCode.OK)
        mockClient.setResponse(response)
        //When
        viewModel.updateSearchQuery("procupine")
        //Then
        viewModel.state.test {
            // Loading State
            assertEquals(FlickrItemState(loading = true), awaitItem())
            // Items loaded by viewmodel
            assertEquals(FlickrItemState(
                loading = false, items = Json.decodeFromString<FlickrResponse>(jsonResponse).items.map { it.toFlickrPhoto() }
            ), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(FlowPreview::class)
    @Test
    fun `test server error updates viewmodel state`() = runTest {
        //Given
        val response = MockResponse("{}", HttpStatusCode.InternalServerError)
        mockClient.setResponse(response)
        //When
        viewModel.updateSearchQuery("procupine")
        //Then
        viewModel.state.test {
            // Loading State
            assertEquals(FlickrItemState(loading = true), awaitItem())
            // Error State
            assertNotNull(awaitItem().error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun readJsonFile(filename: String): String {
        val classLoader = this.javaClass.classLoader
        val inputStream = classLoader?.getResourceAsStream(filename)
            ?: throw IllegalArgumentException("File not found: $filename")
        val reader = BufferedReader(InputStreamReader(inputStream))
        return reader.readText()
    }

}

