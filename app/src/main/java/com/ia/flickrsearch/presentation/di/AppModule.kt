package com.ia.flickrsearch.presentation.di

/**
 * Copyright (c) 2024 AngelLira
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
import com.ia.flickrsearch.data.datasourceimpl.PhotoRemoteDataSourceImpl
import com.ia.flickrsearch.data.repository.PhotoRepositoryImpl
import com.ia.flickrsearch.data.service.PhotoService
import com.ia.flickrsearch.datasource.PhotoRemoteDataSource
import com.ia.flickrsearch.domain.GetPhotosUseCase
import com.ia.flickrsearch.domain.PhotoRepository
import com.ia.flickrsearch.presentation.PhotoViewModel
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@OptIn(FlowPreview::class)
val appModule = module {
    single<PhotoService> { PhotoService(get()) }
    single<PhotoRemoteDataSource> { PhotoRemoteDataSourceImpl(get()) }
    single<PhotoRepository> { PhotoRepositoryImpl(get()) }
    single { GetPhotosUseCase(get()) }
    viewModel { PhotoViewModel(get()) }
}