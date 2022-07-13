package com.example.musinsa.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Good(
    @SerialName("brandName")
    val brandName: String?,
    @SerialName("hasCoupon")
    val hasCoupon: Boolean?,
    @SerialName("linkURL")
    val linkURL: String?,
    @SerialName("price")
    val price: Int?,
    @SerialName("saleRate")
    val saleRate: Int?,
    @SerialName("thumbnailURL")
    val thumbnailURL: String?
)