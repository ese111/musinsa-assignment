package com.example.musinsa.common

import com.example.musinsa.data.dto.HomeDTO

sealed class UiState {
    object Loading : UiState()
    object Empty : UiState()
    data class Success(val data: HomeDTO) : UiState()
    data class Error(val message: String?) : UiState()
}