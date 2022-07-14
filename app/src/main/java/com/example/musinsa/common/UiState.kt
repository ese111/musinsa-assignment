package com.example.musinsa.common

import com.example.musinsa.data.model.HomeData

sealed class UiState {
    object Loading : UiState()
    object Empty : UiState()
    data class Success(val data: List<HomeData>) : UiState()
    data class Error(val message: String?) : UiState()
}