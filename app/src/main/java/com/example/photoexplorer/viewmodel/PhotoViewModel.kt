
package com.example.photoexplorer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoexplorer.data.PhotoApi
import com.example.photoexplorer.model.Photo
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class PhotoUiState {
    object Loading : PhotoUiState()
    data class Success(val photos: List<Photo>) : PhotoUiState()
    data class Error(val message: String) : PhotoUiState()
}

class PhotoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<PhotoUiState>(
        PhotoUiState.Loading
    )

    val uiState: StateFlow<PhotoUiState> = _uiState

    init {
        loadPhotos()
    }

    fun loadPhotos() {
        viewModelScope.launch {
            _uiState.value = PhotoUiState.Loading

            try {
                val photos = PhotoApi.service.getPhotos()

                _uiState.value = PhotoUiState.Success(photos)

            } catch (e: Exception) {
                _uiState.value = PhotoUiState.Error(
                    e.message ?: "No se pudieron cargar las fotografías"
                )
            }
        }
    }
}
