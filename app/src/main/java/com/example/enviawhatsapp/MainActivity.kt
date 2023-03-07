package com.example.enviawhatsapp

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private val BASE_URL = "https://whatsappdirect.vercel.app/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.webView)

        webView.webChromeClient = object : WebChromeClient() {

        }

        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(wv: WebView?, url: String): Boolean {
                if (url.startsWith("tel:") || url.startsWith("whatsapp:") || url.startsWith("intent://") || url.startsWith(
                        "http://"
                    )
                ) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                    webView.goBack()
                    return true
                }
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                invalidateOptionsMenu()
            }

            override fun onReceivedError(
                view: WebView?, errorCode: Int,
                description: String?, failingUrl: String?
            ) {
                try {
                    invalidateOptionsMenu()
                } catch (e: Exception) {
                }
                if (webView.canGoBack()) {
                    webView.goBack()
                }
            }


            override fun onPageFinished(view: WebView?, url: String?) {
                //     pullToRefresh.setRefreshing(false);
                invalidateOptionsMenu()
            }

        }

        val settings = webView.settings
        settings.javaScriptEnabled = true

        webView.loadUrl(BASE_URL)

    }
}
