package com.example.musinsa.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musinsa.common.UiState
import com.example.musinsa.data.model.ContentsData
import com.example.musinsa.data.model.GoodData
import com.example.musinsa.data.model.HomeData
import com.example.musinsa.data.model.StyleData
import com.example.musinsa.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {
    private val originHomeData = MutableStateFlow<UiState<List<HomeData>>>(UiState.Empty)

    private val _homeData = MutableStateFlow<UiState<List<HomeData>>>(UiState.Empty)
    val homeData: StateFlow<UiState<List<HomeData>>> = _homeData

    private var currentGridPage = 0

    private var currentStylePage = 0

    init {
        setHomeData()
    }

    private fun setHomeData() {
        viewModelScope.launch {
            _homeData.value = UiState.Loading
            homeRepository.getHomeData()
                .catch {
                    _homeData.value = UiState.Error("네트워크 연결 실패")
                }.collect {
                    _homeData.value = it?.let { data ->
                        setOriginHomeData(data)
                        UiState.Success(getPagingHomeDate(data))
                    } ?: UiState.Error("data load fail")
                }
        }
    }

    private fun setOriginHomeData(data: List<HomeData>) {
        originHomeData.value = UiState.Success(data)
    }

    private fun getPagingHomeDate(data: List<HomeData>): List<HomeData> {
        val homeDataList = mutableListOf<HomeData>()
        data.forEach { homeData ->
            when (homeData.contents.type) {
                "GRID" -> {
                    val pagingData = HomeData(
                        setPagingGridData(homeData.contents),
                        homeData.footer,
                        homeData.header
                    )
                    homeDataList.add(pagingData)
                }
                "STYLE" -> {
                    val pagingData = HomeData(
                        setPagingStyleData(homeData.contents),
                        homeData.footer,
                        homeData.header
                    )
                    homeDataList.add(pagingData)
                }
                else -> {
                    homeDataList.add(homeData)
                }
            }
        }
        return homeDataList
    }

    private fun setPagingStyleData(contents: ContentsData): ContentsData {
        val afterPagingList = mutableListOf<StyleData>()

        for (i in 0 until 4) {
            afterPagingList.add(contents.styles[i])
            currentStylePage = i
        }
        return ContentsData(styles = afterPagingList, type = contents.type)
    }

    private fun setPagingGridData(contents: ContentsData): ContentsData {
        val afterPagingList = mutableListOf<GoodData>()

        for (i in 0 until 6) {
            afterPagingList.add(contents.goods[i])
            currentGridPage = i
        }
        return ContentsData(goods = afterPagingList, type = contents.type)
    }

    fun setNextStylePage(position: Int) {
        val originHomeData = originHomeData.value._data?.get(position) ?: return
        val currentData = _homeData.value._data?.get(position) ?: return
        val styles = mutableListOf<StyleData>()

        styles.addAll(currentData.contents.styles)
        repeat(2) {
            if (currentStylePage + 1 < originHomeData.contents.styles.size) {
                styles.add(originHomeData.contents.styles[currentStylePage + 1])
                currentStylePage++
            }
        }

        setChangeHomData(
            position,
            HomeData(
                header = originHomeData.header,
                footer = originHomeData.footer,
                contents = ContentsData(
                    styles = styles,
                    type = originHomeData.contents.type,
                    isEndPage = isEndPage(currentStylePage, originHomeData.contents.styles.size)
                )
            )
        )
    }

    private fun isEndPage(currentPage: Int,originDataSize: Int): Boolean {
        var isEndPage = false
        if (currentPage == originDataSize - 1) {
            isEndPage = true
        }
        return isEndPage
    }

    fun setNextGridPage(position: Int) {
        val originHomeData = originHomeData.value._data?.get(position) ?: return
        val currentData = _homeData.value._data?.get(position) ?: return

        val tmp = mutableListOf<GoodData>()
        tmp.addAll(currentData.contents.goods)

        repeat(3) {
            if (currentGridPage + 1 < originHomeData.contents.goods.size) {
                tmp.add(originHomeData.contents.goods[currentGridPage + 1])
                currentGridPage++
            }
        }

        setChangeHomData(
            position,
            HomeData(
                header = originHomeData.header,
                footer = originHomeData.footer,
                contents = ContentsData(
                    goods = tmp,
                    type = originHomeData.contents.type,
                    isEndPage = isEndPage(currentGridPage, originHomeData.contents.goods.size)
                )
            )
        )
    }

    fun shuffleData(position: Int) {
        val originHomeData = _homeData.value._data?.get(position) ?: return
        val newGoods = getShuffleList(originHomeData)

        setChangeHomData(
            position,
            HomeData(
                header = originHomeData.header,
                footer = originHomeData.footer,
                contents = ContentsData(goods = newGoods, type = originHomeData.contents.type)
            )
        )
    }

    private fun getShuffleList(originHomeData: HomeData): List<GoodData> {
        val randomNumbers = getRandomDataMap(originHomeData.contents.goods.size)
        val newGoods = mutableListOf<GoodData>()
        randomNumbers.forEach { (k, v) ->
            newGoods.add(v, originHomeData.contents.goods[k])
        }
        return newGoods
    }

    private fun getRandomDataMap(size: Int): Map<Int, Int> {
        val randomNumbers = mutableMapOf<Int, Int>()

        var i = 0
        while (i < size) {
            val num = Random.nextInt(size)
            if (!randomNumbers.containsKey(num)) {
                randomNumbers[num] = i
                i++
            }
        }

        return randomNumbers
    }

    private fun setChangeHomData(position: Int, data: HomeData) {
        val list = mutableListOf<HomeData>()

        _homeData.value._data?.forEachIndexed { index, homeData ->
            if (index != position) {
                list.add(homeData)
            } else {
                list.add(data)
            }
        }

        _homeData.value = UiState.Success(list)
    }
}

