package com.ia.flickrsearch.photo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ia.flickrsearch.photo.data.model.FlickrItemState
import com.ia.flickrsearch.photo.domain.usecase.GetPhotosUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Copyright (c) 2024 AngelLira
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
@FlowPreview
class PhotoViewModel(
    private val useCase: GetPhotosUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FlickrItemState())
    val state: StateFlow<FlickrItemState> = _state

    fun updateSearchQuery(query: String) {
        _state.value = _state.value.copy(loading = true)
        viewModelScope.launch {
            useCase.execute(query)
                .onEach { result ->
                    _state.value = _state.value.copy(
                        items = result,
                        loading = false,
                        error = null
                    )
                }
                .catch { e ->
                    _state.value = _state.value.copy(
                        loading = false,
                        error = e.message
                    )
                }
                .launchIn(this)
        }
    }
}