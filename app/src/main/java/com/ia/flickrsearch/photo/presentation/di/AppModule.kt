package com.ia.flickrsearch.photo.presentation.di

/**
 * Copyright (c) 2024 AngelLira
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
import com.ia.flickrsearch.photo.data.datasourceimpl.PhotoRemoteDataSourceImpl
import com.ia.flickrsearch.photo.data.repositoryimpl.PhotoRepositoryImpl
import com.ia.flickrsearch.photo.data.service.PhotoService
import com.ia.flickrsearch.photo.data.datasource.PhotoRemoteDataSource
import com.ia.flickrsearch.photo.domain.usecase.GetPhotosUseCase
import com.ia.flickrsearch.photo.domain.repository.PhotoRepository
import com.ia.flickrsearch.photo.presentation.viewmodel.PhotoViewModel
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