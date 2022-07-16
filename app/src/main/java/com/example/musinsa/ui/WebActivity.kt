package com.example.musinsa.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.musinsa.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {

    private val binding: ActivityWebBinding by lazy {
        ActivityWebBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val link = intent.getStringExtra("link")
        startProgressbar()
        link?.let { initWebView(it) }
    }

    private fun endProgressbar() {
        binding.pbWebLoading.visibility = View.GONE
    }

    private fun startProgressbar() {
        binding.pbWebLoading.visibility = View.VISIBLE
    }

    private fun initWebView(link: String) {
        binding.wvWeb.apply {
            webViewClient = AppWebViewClient()
            loadUrl(link)
            settings.domStorageEnabled = true
        }
    }

    inner class AppWebViewClient: WebViewClient() {

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            Toast.makeText(this@WebActivity, "유효하지 않은 링크입니다.", Toast.LENGTH_SHORT).show()
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            startProgressbar()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            endProgressbar()
        }
    }
}