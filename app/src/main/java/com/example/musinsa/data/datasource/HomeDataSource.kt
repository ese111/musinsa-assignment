package com.example.musinsa.data.datasource

import com.example.musinsa.network.HomeApi
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeDataSource @Inject constructor(private val api: HomeApi) {

    fun getHomeData() = flow {
        emit(api.getHomeData())
    }

}