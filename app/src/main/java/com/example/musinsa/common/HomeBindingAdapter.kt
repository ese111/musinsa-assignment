package com.example.musinsa.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import com.example.musinsa.data.model.FooterData
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.*

@BindingAdapter("setImage")
fun setImage(imageView: ImageView, link: String) {
    when(link) {
        "" -> imageView.visibility = View.GONE
        else -> imageView.load(link)
    }
}

@BindingAdapter("setAllButton")
fun setAllButton(textView: TextView, link: String) {
    when(link) {
        "" -> textView.visibility = View.GONE
        else -> textView.visibility = View.VISIBLE
    }
}

@BindingAdapter("setFooter")
fun setFooter(button: MaterialButton, data: FooterData) {
    button.text = data.title
    if(data.iconURL != "" ) {
        val ceh = CoroutineExceptionHandler { _, throwable ->
            Logger("$throwable")
        }

        CoroutineScope(SupervisorJob() + ceh).launch {
            val request = ImageRequest.Builder(button.context).data(data.iconURL).build()
            val drawable = ImageLoader(button.context).execute(request).drawable
            button.icon = drawable
        }
    }
}