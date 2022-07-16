package com.example.musinsa.common

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.musinsa.R
import kotlinx.coroutines.*
import java.text.DecimalFormat

@BindingAdapter("setSaleRate")
fun setSaleRate(textView: TextView, rate: Int) {
    when(rate) {
        0 -> textView.visibility = View.GONE
        else -> textView.text = textView.context.getString(R.string.sale_rate, rate)
    }
}

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("price")
fun setPrice(textView: TextView, price: Int) {
    val format = DecimalFormat("#,###")
    textView.text = format.format(price)
}

@BindingAdapter("icon")
fun setTitleIcon(textView: TextView, iconLink: String) {
    val ceh = CoroutineExceptionHandler { _, throwable ->
        logger("$throwable")
    }
    //초기화
    textView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)

    if(iconLink != "") {
        CoroutineScope(Job() + ceh).launch(Dispatchers.Main.immediate) {
            val drawable = getDrawable(textView.context, iconLink)
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
        }
    }
}