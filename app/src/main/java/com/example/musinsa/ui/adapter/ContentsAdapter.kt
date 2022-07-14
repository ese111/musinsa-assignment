package com.example.musinsa.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musinsa.data.model.GoodData
import com.example.musinsa.databinding.ItemContentsGridBinding

class ContentsAdapter: ListAdapter<GoodData, ContentsAdapter.ContentsViewHolder>(ContentsDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentsViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ContentsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class ContentsViewHolder(private val binding: ItemContentsGridBinding): RecyclerView.ViewHolder(binding.root) {

    }

    private object ContentsDiffUtil: DiffUtil.ItemCallback<GoodData>() {

        override fun areItemsTheSame(oldItem: GoodData, newItem: GoodData) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: GoodData, newItem: GoodData) =
            oldItem.thumbnailURL == newItem.thumbnailURL

    }

}