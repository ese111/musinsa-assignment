package com.example.musinsa.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musinsa.common.ItemClickListener
import com.example.musinsa.data.model.GoodData
import com.example.musinsa.databinding.ItemContentsGridBinding

class ContentsAdapter(private val listener: ItemClickListener) :
    ListAdapter<GoodData, ContentsAdapter.ContentsViewHolder>(ContentsDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentsViewHolder {
        return ContentsViewHolder(
            ItemContentsGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ContentsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ContentsViewHolder(private val binding: ItemContentsGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GoodData) {
            binding.item = item

            itemView.setOnClickListener {
                listener.moveToWeb(item.linkURL)
            }
        }

    }

    private object ContentsDiffUtil : DiffUtil.ItemCallback<GoodData>() {

        override fun areItemsTheSame(oldItem: GoodData, newItem: GoodData) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: GoodData, newItem: GoodData) =
            oldItem.thumbnailURL == newItem.thumbnailURL

    }

}