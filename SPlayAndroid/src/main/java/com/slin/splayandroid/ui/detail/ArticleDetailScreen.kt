package com.slin.splayandroid.ui.detail

import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.layout.statusBarsPadding

/**
 * author: slin
 * date: 2021/9/2
 * description:
 *
 */

@Composable
fun ArticleDetailScreen(mTitle: String, mUrl: String, onBackPress: () -> Unit) {
    val rememberTitle = remember { mutableStateOf(mTitle) }
    var webView: WebView? = null

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = rememberTitle.value,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                if (webView?.canGoBack() == true) {
                                    webView?.goBack()
                                } else {
                                    onBackPress()
                                }
                            }
                            .padding(16.dp)
                    )
                },
                modifier = Modifier
                    .background(MaterialTheme.colors.primary)
                    .statusBarsPadding(),
                elevation = 0.dp
            )
        },
    ) { _ ->
        AndroidView(factory = { context ->
            WebView(context).apply {
                webView = this
                loadUrl(mUrl)
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webChromeClient = object : WebChromeClient() {
                    override fun onReceivedTitle(view: WebView?, title: String?) {
                        super.onReceivedTitle(view, title)
                        if (title.isNullOrEmpty().not()) {
                            rememberTitle.value = title!!
                        }
                    }
                }
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?,
                    ): Boolean {
                        return super.shouldOverrideUrlLoading(view, request)
                    }
                }
            }
        }, modifier = Modifier.fillMaxSize())
    }
}
