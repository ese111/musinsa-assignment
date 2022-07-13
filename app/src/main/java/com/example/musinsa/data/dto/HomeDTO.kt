package com.example.musinsa.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeDTO(
    @SerialName("data")
    val data: List<Data?>?
)

@Serializable
data class Data(
    @SerialName("contents")
    val contents: Contents?,
    @SerialName("footer")
    val footer: Footer?,
    @SerialName("header")
    val header: Header?
)

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

@Serializable
data class Header(
    @SerialName("iconURL")
    val iconURL: String?,
    @SerialName("linkURL")
    val linkURL: String?,
    @SerialName("title")
    val title: String?
)

@Serializable
data class Footer(
    @SerialName("iconURL")
    val iconURL: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("type")
    val type: String?
)

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
    val saleRate: Int? = 0,
    @SerialName("thumbnailURL")
    val thumbnailURL: String? = ""
)

@Serializable
data class Style(
    @SerialName("linkURL")
    val linkURL: String? = "",
    @SerialName("thumbnailURL")
    val thumbnailURL: String? = ""
)

@Serializable
data class Banner(
    @SerialName("description")
    val description: String? = "",
    @SerialName("keyword")
    val keyword: String? = "",
    @SerialName("linkURL")
    val linkURL: String? = "",
    @SerialName("thumbnailURL")
    val thumbnailURL: String? = "",
    @SerialName("title")
    val title: String? = ""
)