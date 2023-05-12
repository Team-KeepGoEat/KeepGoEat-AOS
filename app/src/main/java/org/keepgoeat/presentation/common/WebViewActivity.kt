package org.keepgoeat.presentation.common

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import org.keepgoeat.R
import org.keepgoeat.databinding.ActivityWebViewBinding
import org.keepgoeat.presentation.base.screen.BindingActivity

class WebViewActivity : BindingActivity<ActivityWebViewBinding>(R.layout.activity_web_view) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()

            settings.apply {
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                loadWithOverviewMode = true
                useWideViewPort = true
                domStorageEnabled = true
                setSupportZoom(true)
            }

            intent.getStringExtra(ARG_WEB_VIEW_LINK)?.let { loadUrl(it) }
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    companion object {
        const val ARG_WEB_VIEW_LINK = "link"
    }
}
