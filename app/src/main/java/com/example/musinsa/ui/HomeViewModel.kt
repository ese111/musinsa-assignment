package com.example.musinsa.ui

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musinsa.common.UiState
import com.example.musinsa.common.logger
import com.example.musinsa.data.dto.HomeDTO
import com.example.musinsa.data.model.GoodData
import com.example.musinsa.data.model.HomeData
import com.example.musinsa.data.model.StyleData
import com.example.musinsa.data.repository.HomeRepository
import com.example.musinsa.ui.adapter.ContentsAdapter
import com.example.musinsa.ui.adapter.StylesAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _homeData = MutableStateFlow<UiState<List<HomeData>>>(UiState.Empty)
    val homeData: StateFlow<UiState<List<HomeData>>> = _homeData

    private val _gridItemList = MutableStateFlow<UiState<List<GoodData>>>(UiState.Empty)
    val gridItemList: StateFlow<UiState<List<GoodData>>> = _gridItemList

    private val _styleItemList = MutableStateFlow<UiState<List<StyleData>>>(UiState.Empty)
    val styleItemList: StateFlow<UiState<List<StyleData>>> = _styleItemList

    private var _gridPaging = 0
    val gridPaging = _gridPaging

    private var _stylePaging = 0
    val stylePaging = _stylePaging

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
                    _homeData.value = it?.let { data ->
                        data.forEachIndexed { i, homeData ->
//                            when (homeData.contents.type) {
//                                "SCROLL" -> {
//                                        val size = homeData.contents.goods.size
//                                        val random = Random( size - 1)
//                                        val tmpList = mutableListOf<GoodData>()
//                                        val randomNumbers = mutableMapOf<Int, Int>()
//                                        var i = 0
//                                        while (i < size) {
//                                            val num = random.nextInt()
//                                            if(randomNumbers.containsKey(num)) {
//                                                randomNumbers[num] = i
//                                                i++
//                                            }
//                                        }
//                                        for ((k, v) in randomNumbers.entries) {
//                                            tmpList[v] = homeData.contents.goods[k]
//                                        }
//
//                                }
//
//                                "GRID" -> {
//                                    val tmpList = mutableListOf<GoodData>()
//                                    for (i in 0 until 6) {
//                                        tmpList.add(homeData.contents.goods[i])
//                                        _homeData.value._data?.get(i).contents.goods = tmpList
//                                        _gridPaging++
//                                    }
//
//
//                                    binding.btnMore.setOnClickListener {
//                                        logger("grid click!")
//                                        for (i in 1..3) {
//                                            if(gridPaging + i < content.contents.goods.size) {
//                                                gridItemList.add(content.contents.goods[gridPaging + i])
//                                            } else {
//                                                binding.btnMore.visibility = View.GONE
//                                            }
//                                        }
//                                        adapter.submitList(gridItemList)
//                                    }
//                                }
//
//                                "STYLE" -> {
//                                    val adapter = StylesAdapter()
//                                    binding.rvContents.adapter = adapter
//                                    binding.rvContents.layoutManager = GridLayoutManager(context, 2)
//                                    for (i in 0 until 4) {
//                                        styleItemList.add(content.contents.styles[i])
//                                        stylePaging++
//                                    }
//                                    adapter.submitList(styleItemList)
//
//                                    binding.btnMore.setOnClickListener {
//                                        logger("style click!")
//                                        for (i in 1..2) {
//                                            if(stylePaging + i < content.contents.styles.size) {
//                                                styleItemList.add(content.contents.styles[stylePaging + i])
//                                                logger("style add")
//                                            }else {
//                                                binding.btnMore.visibility = View.GONE
//                                            }
//                                        }
//                                        adapter.submitList(styleItemList)
//
//                                    }
//                                }
//                            }

                        }
                        UiState.Success(data)
                    } ?: UiState.Error("네트워크 연결 실패")
                }
        }
    }

    private fun getHomeDataAfterPaging(data: HomeData) {

    }
}

