package com.example.musinsa.data.model

sealed class ModelData {}

data class HomeData(
    val contents: ContentsData,
    val footer: FooterData,
    val header: HeaderData
)

data class ContentsData(
    val banners: List<BannerData>,
    val goods: List<GoodData>,
    val styles: List<StyleData>,
    val type: String
)

data class HeaderData(
    val iconURL: String,
    val linkURL: String,
    val title: String
)

data class FooterData(
    val iconURL: String,
    val title: String,
    val type: String
)

data class GoodData(
    val brandName: String,
    val hasCoupon: Boolean,
    val linkURL: String,
    val price: Int,
    val saleRate: Int,
    val thumbnailURL: String
)

data class StyleData(
    val linkURL: String,
    val thumbnailURL: String
)

data class BannerData(
    val description: String,
    val keyword: String,
    val linkURL: String,
    val thumbnailURL: String,
    val title: String
)


