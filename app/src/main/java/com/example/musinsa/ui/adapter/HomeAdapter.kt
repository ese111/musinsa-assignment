package com.example.musinsa.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import androidx.viewpager2.widget.ViewPager2
import com.example.musinsa.common.Logger
import com.example.musinsa.data.model.HomeData
import com.example.musinsa.data.model.StyleData
import com.example.musinsa.databinding.ItemBannersBinding
import com.example.musinsa.databinding.ItemContentsBinding
import com.example.musinsa.databinding.ItemContentsStyleBinding

private const val BANNERS = 0
private const val CONTENTS = 1
private const val STYLES = 2

class HomeAdapter(private val context: Context) : ListAdapter<HomeData, RecyclerView.ViewHolder>(HomeDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Logger("onCreateViewHolder")
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
        Logger("onBindViewHolder")
        when (holder) {
            is BannerViewHolder -> holder.bind(getItem(position))
            is ContentViewHolder -> holder.bind(getItem(position))
            is StylesViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).contents.type) {
            "BANNER" -> BANNERS
            "STYLE" -> STYLES
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

        fun bind(content: HomeData) {
            binding.item = content
            val adapter = ContentsAdapter()
            binding.rvContents.adapter = adapter
            when(content.contents.type) {
                "SCROLL" -> binding.rvContents.layoutManager = LinearLayoutManager(context)
                "GRID" -> binding.rvContents.layoutManager = GridLayoutManager(context, 3)
            }
        }
    }

    class StylesViewHolder(private val binding: ItemContentsStyleBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(style: HomeData) {

            }
    }

    private object HomeDiffUtil : DiffUtil.ItemCallback<HomeData>() {

        override fun areItemsTheSame(oldItem: HomeData, newItem: HomeData) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: HomeData, newItem: HomeData) =
            oldItem.contents.type == newItem.contents.type

    }

}