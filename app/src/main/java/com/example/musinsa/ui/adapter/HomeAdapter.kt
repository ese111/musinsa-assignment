package com.example.musinsa.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import androidx.viewpager2.widget.ViewPager2
import com.example.musinsa.common.ItemClickListener
import com.example.musinsa.data.model.HomeData
import com.example.musinsa.databinding.ItemBannersBinding
import com.example.musinsa.databinding.ItemContentsBinding

private const val BANNERS = 0
private const val CONTENTS = 1

class HomeAdapter(private val context: Context, private val listener: ItemClickListener) :
    ListAdapter<HomeData, RecyclerView.ViewHolder>(HomeDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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
        when (holder) {
            is BannerViewHolder -> holder.bind(getItem(position))
            is ContentViewHolder -> holder.bind(getItem(position), position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).contents.type) {
            "BANNER" -> BANNERS
            else -> CONTENTS
        }
    }

    private inner class BannerViewHolder(private val binding: ItemBannersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(banner: HomeData) {
            val adapter = BannerItemAdapter(listener)

            binding.vpBannersView.adapter = adapter

            binding.size = banner.contents.banners.size

            setCurrentBanner()
            adapter.submitList(banner.contents.banners)
        }

        private fun setCurrentBanner() {
            binding.vpBannersView.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.current = position + 1
                }
            })
        }
    }

    private inner class ContentViewHolder(private val binding: ItemContentsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contents: HomeData, position: Int) {
            binding.item = contents

            when (contents.contents.type) {
                "SCROLL" -> {
                    val adapter = ContentsAdapter(listener)
                    binding.rvContents.adapter = adapter
                    binding.rvContents.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

                    adapter.submitList(contents.contents.goods)

                    setAllButton(contents.header.linkURL)

                    setShuffleButton(position)
                }

                "GRID" -> {
                    val adapter = ContentsAdapter(listener)
                    binding.rvContents.adapter = adapter
                    binding.rvContents.layoutManager = GridLayoutManager(context, 3)
                    adapter.submitList(contents.contents.goods)

                    setButtonVisible(contents.contents.isEndPage)

                    setMoreButton(contents.contents.type, position, contents.contents.goods.size)
                }

                "STYLE" -> {
                    val adapter = StylesAdapter(listener)
                    binding.rvContents.adapter = adapter
                    binding.rvContents.layoutManager = GridLayoutManager(context, 2)

                    adapter.submitList(contents.contents.styles)

                    setButtonVisible(contents.contents.isEndPage)

                    setMoreButton(contents.contents.type, position, contents.contents.styles.size)
                }
            }
        }

        private fun setMoreButton(type: String, position: Int, size: Int) {
            binding.btnMore.setOnClickListener {
                when (type) {
                    "GRID" -> listener.setNextGridPage(position)
                    "STYLE" -> listener.setNextStylePage(position)
                }
                binding.rvContents.postDelayed({
                    binding.rvContents.smoothScrollBy(1, 0)
                    binding.rvContents.smoothScrollToPosition(3)
                }, 300)
            }
        }

        private fun setButtonVisible(isEndPage: Boolean) {
            if (isEndPage) {
                binding.btnMore.visibility = View.GONE
            }
        }

        private fun setAllButton(link: String) {
            binding.tvAll.setOnClickListener {
                listener.moveToWeb(link)
            }
        }

        private fun setShuffleButton(position: Int) {
            binding.btnMore.setOnClickListener {
                listener.shuffleData(position)
            }
        }
    }

    private object HomeDiffUtil : DiffUtil.ItemCallback<HomeData>() {

        override fun areItemsTheSame(oldItem: HomeData, newItem: HomeData): Boolean =
            oldItem.header.title == newItem.header.title


        override fun areContentsTheSame(oldItem: HomeData, newItem: HomeData): Boolean =
            oldItem.contents.hashCode() == newItem.contents.hashCode()

    }

}