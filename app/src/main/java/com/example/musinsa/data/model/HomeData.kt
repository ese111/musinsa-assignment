package com.example.musinsa.data.model

data class HomeData(
    val contents: ContentsData = ContentsData(),
    val footer: FooterData = FooterData(),
    val header: HeaderData = HeaderData()
)

data class ContentsData(
    val banners: List<BannerData> = emptyList(),
    val goods: List<GoodData> = emptyList(),
    val styles: List<StyleData> = emptyList(),
    val type: String = "",
    val isEndPage: Boolean = false
)

data class GoodData(
    val brandName: String,
    val hasCoupon: Boolean,
    val linkURL: String,
    val price: Int,
    val saleRate: Int,
    val thumbnailURL: String,
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

data class HeaderData(
    val iconURL: String = "",
    val linkURL: String = "",
    val title: String = ""
)

data class FooterData(
    val iconURL: String = "",
    val title: String = "",
    val type: String = ""
)






