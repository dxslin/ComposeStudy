package com.slin.splayandroid.ui.detail

import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.insets.statusBarsPadding

/**
 * author: slin
 * date: 2021/9/2
 * description:
 *
 */

@Composable
fun ArticleDetailScreen(mTitle: String, mUrl: String) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
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
            }, navigationIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            if (webView?.canGoBack() == true) {
                                webView?.goBack()
                            } else {
                                onBackPressedDispatcher?.onBackPressed()
                            }
                        }
                        .padding(16.dp)
                )
                }, modifier = Modifier
                    .background(MaterialTheme.colors.primary)
                    .statusBarsPadding(),
                elevation = 0.dp
            )
        }) {
        AndroidView(factory = { context ->
            WebView(context).apply {
                webView = this
                loadUrl(mUrl)
                settings.javaScriptEnabled = true
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
                        request: WebResourceRequest?
                    ): Boolean {
                        return super.shouldOverrideUrlLoading(view, request)
                    }
                }
            }
        })
    }
}