package com.example.musinsa.network

import com.example.musinsa.data.dto.HomeDTO
import retrofit2.http.GET

interface HomeApi {

    @GET("interview/list.json")
    suspend fun getHomeData(): HomeDTO
}