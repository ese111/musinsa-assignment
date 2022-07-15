package com.example.musinsa.ui

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.musinsa.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {

    private val binding: ActivityWebBinding by lazy {
        ActivityWebBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val link = intent.getStringExtra("link")
        binding.pbWebLoading.visibility = View.GONE
        link?.let { initWeb(it) }
    }

    private fun initWeb(link: String) {
        binding.wvWeb.apply {
            webViewClient = AppWebViewClient()
            loadUrl(link)
        }

    }

    inner class AppWebViewClient: WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            binding.pbWebLoading.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.pbWebLoading.visibility = View.GONE
        }
    }
}