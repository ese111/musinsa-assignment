package com.example.musinsa.data.repository

import com.example.musinsa.data.datasource.HomeDataSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(private val dataSource: HomeDataSource) {

    fun getHomeData() = dataSource.getHomeData()
}