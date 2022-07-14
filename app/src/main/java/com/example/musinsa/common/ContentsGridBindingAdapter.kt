package com.example.musinsa.common

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.musinsa.R
import java.text.DecimalFormat

@BindingAdapter("setSaleRate")
fun setSaleRate(textView: TextView, rate: Int) {
    when(rate) {
        0 -> textView.visibility = View.GONE
        else -> textView.text = textView.context.getString(R.string.sale_rate, rate)
    }
}

@BindingAdapter("app:goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("app:price")
fun setPrice(textView: TextView, price: Int) {
    val format = DecimalFormat("#,###")
    textView.text = format.format(price)
}