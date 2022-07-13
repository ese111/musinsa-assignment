package com.example.musinsa.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeDTO(
    @SerialName("data")
    val data: List<Data?>?
)