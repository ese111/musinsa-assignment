package com.example.musinsa.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musinsa.data.model.StyleData
import com.example.musinsa.databinding.ItemContentsStyleBinding

class StylesAdapter : ListAdapter<StyleData, StylesAdapter.StyleItemViewHolder>(StyleDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StyleItemViewHolder {
        return StyleItemViewHolder(ItemContentsStyleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StyleItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class StyleItemViewHolder(private val binding: ItemContentsStyleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StyleData) {
            binding.item = item
        }

    }

    private object StyleDiffUtil : DiffUtil.ItemCallback<StyleData>() {

        override fun areItemsTheSame(oldItem: StyleData, newItem: StyleData) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: StyleData, newItem: StyleData) =
            oldItem.thumbnailURL == newItem.thumbnailURL

    }


}