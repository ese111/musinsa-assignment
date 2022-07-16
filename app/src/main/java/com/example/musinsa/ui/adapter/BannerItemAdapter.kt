package com.example.musinsa.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musinsa.common.ItemClickListener
import com.example.musinsa.data.model.BannerData
import com.example.musinsa.databinding.ItemContentsBannersBinding

class BannerItemAdapter(private val listener: ItemClickListener) :
    ListAdapter<BannerData, BannerItemAdapter.BannerItemViewHolder>(BannerItemDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerItemViewHolder {
        return BannerItemViewHolder(
            ItemContentsBannersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BannerItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BannerItemViewHolder(private val binding: ItemContentsBannersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(banner: BannerData) {
            binding.item = banner

            itemView.setOnClickListener {
                listener.moveToWeb(banner.linkURL)
            }
        }

    }

    private object BannerItemDiffUtil : DiffUtil.ItemCallback<BannerData>() {
        override fun areItemsTheSame(oldItem: BannerData, newItem: BannerData) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: BannerData, newItem: BannerData) =
            oldItem.title == newItem.title

    }

}