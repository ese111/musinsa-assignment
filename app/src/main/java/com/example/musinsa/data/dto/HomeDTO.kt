package com.example.musinsa.data.dto


import com.example.musinsa.common.Logger
import com.example.musinsa.data.model.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeDTO(
    @SerialName("data")
    val data: List<Data>?
)

@Serializable
data class Data(
    @SerialName("contents")
    val contents: Contents?,
    @SerialName("footer")
    val footer: Footer = Footer(type = "BANNERS"),
    @SerialName("header")
    val header: Header = Header()
) {

    @Serializable
    data class Header(
        @SerialName("iconURL")
        val iconURL: String = "",
        @SerialName("linkURL")
        val linkURL: String = "",
        @SerialName("title")
        val title: String = ""
    )


    @Serializable
    data class Contents(
        @SerialName("banners")
        val banners: List<Banner> = emptyList(),
        @SerialName("goods")
        val goods: List<Good> = emptyList(),
        @SerialName("styles")
        val styles: List<Style> = emptyList(),
        @SerialName("type")
        val type: String?
    ) {


        @Serializable
        data class Good(
            @SerialName("brandName")
            val brandName: String?,
            @SerialName("hasCoupon")
            val hasCoupon: Boolean = false,
            @SerialName("linkURL")
            val linkURL: String?,
            @SerialName("price")
            val price: Int?,
            @SerialName("saleRate")
            val saleRate: Int = 0,
            @SerialName("thumbnailURL")
            val thumbnailURL: String = ""
        )


        @Serializable
        data class Style(
            @SerialName("linkURL")
            val linkURL: String?,
            @SerialName("thumbnailURL")
            val thumbnailURL: String?
        )


        @Serializable
        data class Banner(
            @SerialName("description")
            val description: String = "",
            @SerialName("keyword")
            val keyword: String = "",
            @SerialName("linkURL")
            val linkURL: String?,
            @SerialName("thumbnailURL")
            val thumbnailURL: String?,
            @SerialName("title")
            val title: String = ""
        )
    }


    @Serializable
    data class Footer(
        @SerialName("iconURL")
        val iconURL: String = "",
        @SerialName("title")
        val title: String = "",
        @SerialName("type")
        val type: String = ""
    )

}

fun Data.toHomeData(): HomeData {
    val contents = requireNotNull(contents).toContentsData()
    val footer = footer.toFooterData()
    val header = header.toHeaderData()
    return HomeData(contents, footer, header)
}

fun Data.Contents.toContentsData(): ContentsData {
    val type = requireNotNull(type)
    val banners = banners.map { it.toBannerData() }
    val goods = goods.map { it.toGoodData() }
    val styles = styles.map { it.toStyleData() }
    return ContentsData(banners, goods, styles, type)
}

fun Data.Header.toHeaderData() = HeaderData(iconURL, linkURL, title)

fun Data.Footer.toFooterData() = FooterData(iconURL, title, type)

fun Data.Contents.Good.toGoodData(): GoodData {
    val brandName = requireNotNull(brandName)
    val linkURL = requireNotNull(linkURL)
    val price = requireNotNull(price)
    return GoodData(brandName, hasCoupon, linkURL, price, saleRate, thumbnailURL)
}

fun Data.Contents.Style.toStyleData(): StyleData {
    val linkURL = requireNotNull(linkURL)
    val thumbnailURL = requireNotNull(thumbnailURL)
    return StyleData(linkURL, thumbnailURL)
}

fun Data.Contents.Banner.toBannerData(): BannerData {
    val linkURL = requireNotNull(this.linkURL)
    val thumbnailURL = requireNotNull(this.thumbnailURL)
    return BannerData(this.description, this.keyword, linkURL, thumbnailURL, this.title)
}
