package com.example.musinsa.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("contents")
    val contents: Contents?,
    @SerialName("footer")
    val footer: Footer?,
    @SerialName("header")
    val header: Header?
)