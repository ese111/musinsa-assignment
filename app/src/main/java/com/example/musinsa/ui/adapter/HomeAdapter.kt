package com.example.musinsa.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import androidx.viewpager2.widget.ViewPager2
import com.example.musinsa.common.logger
import com.example.musinsa.data.model.GoodData
import com.example.musinsa.data.model.HomeData
import com.example.musinsa.data.model.StyleData
import com.example.musinsa.databinding.ItemBannersBinding
import com.example.musinsa.databinding.ItemContentsBinding

private const val BANNERS = 0
private const val CONTENTS = 1

class HomeAdapter(private val context: Context) :
    ListAdapter<HomeData, RecyclerView.ViewHolder>(HomeDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        logger("onCreateViewHolder")
        return when (viewType) {
            BANNERS -> BannerViewHolder(
                ItemBannersBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> ContentViewHolder(
                ItemContentsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        logger("onBindViewHolder")
        when (holder) {
            is BannerViewHolder -> holder.bind(getItem(position))
            is ContentViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).contents.type) {
            "BANNER" -> BANNERS
            else -> CONTENTS
        }
    }

    class BannerViewHolder(private val binding: ItemBannersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(banner: HomeData) {
            val adapter = BannerItemAdapter()

            binding.vpBannersView.adapter = adapter
            adapter.submitList(banner.contents.banners)

            binding.size = banner.contents.banners.size

            binding.vpBannersView.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.current = position + 1
                }
            })
        }
    }

    inner class ContentViewHolder(private val binding: ItemContentsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val gridItemList = mutableListOf<GoodData>()
        private val styleItemList = mutableListOf<StyleData>()
        private var gridPaging = 0
        private var stylePaging = 0

        fun bind(content: HomeData) {
            binding.item = content

            when (content.contents.type) {
                "SCROLL" -> {
                    val adapter = ContentsAdapter()
                    binding.rvContents.adapter = adapter
                    binding.rvContents.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    logger("SCROLL  ${content.contents.goods}")
                    adapter.submitList(content.contents.goods)
                }

                "GRID" -> {
                    val adapter = ContentsAdapter()
                    binding.rvContents.adapter = adapter
                    binding.rvContents.layoutManager = GridLayoutManager(context, 3)
                    logger("GRID  ${content.contents.goods}")
                    for (i in 0 until 6) {
                        gridItemList.add(content.contents.goods[i])
                        gridPaging++
                    }
                    adapter.submitList(gridItemList)

                }

                "STYLE" -> {
                    val adapter = StylesAdapter()
                    binding.rvContents.adapter = adapter
                    binding.rvContents.layoutManager = GridLayoutManager(context, 2)
                    for (i in 0 until 4) {
                        styleItemList.add(content.contents.styles[i])
                        stylePaging++
                    }
                    adapter.submitList(styleItemList)
                }
            }

        }
    }

    private object HomeDiffUtil : DiffUtil.ItemCallback<HomeData>() {

        override fun areItemsTheSame(oldItem: HomeData, newItem: HomeData) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: HomeData, newItem: HomeData) =
            oldItem.contents.type == newItem.contents.type

    }

}