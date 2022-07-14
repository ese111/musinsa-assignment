package com.example.musinsa.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musinsa.data.dto.Data
import com.example.musinsa.data.dto.HomeDTO
import com.example.musinsa.data.model.HomeData
import com.example.musinsa.databinding.ItemBannersBinding
import com.example.musinsa.databinding.ItemContentsBinding

private const val BANNERS = 0
private const val CONTENTS = 1

class HomeAdapter : ListAdapter<HomeData, RecyclerView.ViewHolder>(HomeDiffUtil) {

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
            is ContentViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> BANNERS
            else -> CONTENTS
        }
    }

    class BannerViewHolder(private val binding: ItemBannersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(banner: HomeData) {

        }
    }

    class ContentViewHolder(private val binding: ItemContentsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(content: HomeData) {

        }
    }

    private object HomeDiffUtil : DiffUtil.ItemCallback<HomeData>() {

        override fun areItemsTheSame(oldItem: HomeData, newItem: HomeData) =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: HomeData, newItem: HomeData) =
            oldItem == newItem

    }

}