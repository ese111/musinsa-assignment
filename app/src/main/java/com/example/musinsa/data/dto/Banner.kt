package com.example.musinsa.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Banner(
    @SerialName("description")
    val description: String?,
    @SerialName("keyword")
    val keyword: String?,
    @SerialName("linkURL")
    val linkURL: String?,
    @SerialName("thumbnailURL")
    val thumbnailURL: String?,
    @SerialName("title")
    val title: String?
)