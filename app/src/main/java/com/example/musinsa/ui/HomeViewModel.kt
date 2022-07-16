package com.example.musinsa.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musinsa.common.UiState
import com.example.musinsa.common.logger
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

    private var gridPaging = 0

    private var stylePaging = 0

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
                        originHomeData.value = UiState.Success(data)
                        val homeDataList = mutableListOf<HomeData>()
                        data.forEachIndexed { index, homeData ->
                            when (homeData.contents.type) {
                                "GRID" -> {
                                    val pagingData = HomeData(
                                        setPagingGridData(index, homeData.contents),
                                        homeData.footer,
                                        homeData.header
                                    )
                                    homeDataList.add(pagingData)
                                }
                                "STYLE" -> {
                                    val pagingData = HomeData(
                                        setPagingStyleData(index, homeData.contents),
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
                        UiState.Success(homeDataList)
                    } ?: UiState.Error("data load fail")
                }
        }
    }

    private fun setPagingStyleData(position: Int, contents: ContentsData): ContentsData {
        val tmpList = mutableListOf<StyleData>()
        for (i in 0 until 4) {
            tmpList.add(contents.styles[i])
            stylePaging = i
        }
        return ContentsData(styles = tmpList, type = contents.type)
    }

    private fun setPagingGridData(position: Int, contents: ContentsData): ContentsData {
        val tmpList = mutableListOf<GoodData>()

        for (i in 0 until 6) {
            tmpList.add(contents.goods[i])
            gridPaging = i
        }
        return ContentsData(goods = tmpList, type = contents.type)
    }

    fun setNextStylePage(position: Int) {
        val originHomeData = originHomeData.value._data?.get(position) ?: return
        val tmp = mutableListOf<StyleData>()
        val currentData = _homeData.value._data?.get(position) ?: return
        var isEndPage = false
        tmp.addAll(currentData.contents.styles)
        repeat ( 2) {
            if (stylePaging + 1 < originHomeData.contents.styles.size) {
                tmp.add(originHomeData.contents.styles[stylePaging + 1])
                stylePaging++
            }
        }

        if(stylePaging == originHomeData.contents.styles.size - 1) {
            isEndPage = true
        }

        setChangeHomData(
            position,
            HomeData(
                header = originHomeData.header,
                footer = originHomeData.footer,
                contents = ContentsData(styles = tmp, type = originHomeData.contents.type, isEndPage = isEndPage)
            )
        )
    }

    fun setNextGridPage(position: Int) {
        val originHomeData = originHomeData.value._data?.get(position) ?: return
        val currentData = _homeData.value._data?.get(position) ?: return
        val tmp = mutableListOf<GoodData>()
        var isEndPage = false
        tmp.addAll(currentData.contents.goods)
        repeat (3) {
            if (gridPaging + 1 < originHomeData.contents.goods.size) {
                tmp.add(originHomeData.contents.goods[gridPaging + 1])
                gridPaging++
            }
        }
        if(gridPaging == originHomeData.contents.goods.size - 1) {
            isEndPage = true
        }
        setChangeHomData(
            position,
            HomeData(
                header = originHomeData.header,
                footer = originHomeData.footer,
                contents = ContentsData(goods = tmp, type = originHomeData.contents.type, isEndPage = isEndPage)
            )
        )
    }

    fun shuffleData(position: Int) {
        val originHomeData = _homeData.value._data?.get(position) ?: return
        val size = originHomeData.contents.goods.size
        val random = Random
        val tmpList = mutableListOf<GoodData>()
        val randomNumbers = mutableMapOf<Int, Int>()
        var i = 0

        while (i < size) {
            val num = random.nextInt(size)
            if (!randomNumbers.containsKey(num)) {
                randomNumbers[num] = i
                i++
            }
        }
        randomNumbers.forEach { (k, v) ->
            tmpList.add(v, originHomeData.contents.goods[k])
        }

        setChangeHomData(
            position,
            HomeData(
                header = originHomeData.header,
                footer = originHomeData.footer,
                contents = ContentsData(goods = tmpList, type = originHomeData.contents.type)
            )
        )
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

