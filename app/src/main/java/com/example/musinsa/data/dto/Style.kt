package com.example.musinsa.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Style(
    @SerialName("linkURL")
    val linkURL: String?,
    @SerialName("thumbnailURL")
    val thumbnailURL: String?
)