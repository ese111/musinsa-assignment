package com.example.musinsa.data.repository

import com.example.musinsa.data.datasource.HomeDataSource
import com.example.musinsa.data.dto.toHomeData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(private val dataSource: HomeDataSource) {

    fun getHomeData() = flow {
        dataSource.getHomeData().map { data ->
            data.data?.map { it.toHomeData() }
        }.collect {
            emit(it)
        }
    }
}