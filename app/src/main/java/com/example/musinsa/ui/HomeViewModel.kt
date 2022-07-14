package com.example.musinsa.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musinsa.common.UiState
import com.example.musinsa.data.dto.HomeDTO
import com.example.musinsa.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _homeData = MutableStateFlow<UiState>(UiState.Empty)
    val homeData: StateFlow<UiState> = _homeData

    init {
        getHomeData()
    }

    private fun getHomeData() {
        viewModelScope.launch {
            _homeData.value = UiState.Loading
            homeRepository.getHomeData()
                .catch {
                    _homeData.value = UiState.Error("네트워크 연결 실패")
                }.collect {
                    _homeData.value = it?.let { data -> UiState.Success(data) } ?: UiState.Error("네트워크 연결 실패")
                }
        }
    }
}

