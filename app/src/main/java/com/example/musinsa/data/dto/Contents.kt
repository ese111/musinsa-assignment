package com.example.musinsa.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Contents(
    @SerialName("banners")
    val banners: List<Banner?>?,
    @SerialName("goods")
    val goods: List<Good?>?,
    @SerialName("styles")
    val styles: List<Style?>?,
    @SerialName("type")
    val type: String?
)