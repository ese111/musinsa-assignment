package com.example.musinsa.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.musinsa.data.model.FooterData
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@BindingAdapter("setImage")
fun setImage(imageView: ImageView, link: String) {
    when (link) {
        "" -> imageView.visibility = View.GONE
        else -> imageView.load(link)
    }
}

@BindingAdapter("setAllButton")
fun setAllButton(textView: TextView, link: String) {
    when (link) {
        "" -> textView.visibility = View.GONE
        else -> textView.visibility = View.VISIBLE
    }
}

@BindingAdapter("setFooter")
fun setFooter(button: MaterialButton, data: FooterData) {
    button.text = data.title
    if (data.iconURL != "") {
        CoroutineScope(Job()).launch(Dispatchers.Main.immediate) {
            val drawable = getDrawable(button.context, data.iconURL)
            button.icon = drawable
        }

    }
}