package com.slin.splayandroid.ui.detail

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView
import com.slin.splayandroid.base.ComposeFragment

/**
 * author: slin
 * date: 2021/9/1
 * description:
 *
 */

private const val ARG_TITLE = "title"
private const val ARG_URL = "url"

class ArticleDetailFragment : ComposeFragment() {

    private lateinit var mTitle: String
    private lateinit var mUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mTitle = it.getString(ARG_TITLE, "")
            mUrl = it.getString(ARG_URL, "")
        }
    }

    @Composable
    override fun ComposeFragment.Content() {
        val rememberTitle = remember { mutableStateOf(this@ArticleDetailFragment.mTitle) }
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(
                    text = rememberTitle.value,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }, navigationIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            })
        }) {
            AndroidView(factory = { context ->
                WebView(context).apply {
                    url?.let { it1 -> loadUrl(it1) }
                    settings.javaScriptEnabled = true
                    webChromeClient = object : WebChromeClient() {
                        override fun onReceivedTitle(view: WebView?, title: String?) {
                            super.onReceivedTitle(view, title)
                            if (title.isNullOrEmpty().not()) {
                                rememberTitle.value = title!!
                            }
                        }
                    }
                }
            })
        }

    }


    companion object {
        fun getInstance(url: String, title: String = ""): ArticleDetailFragment {
            return ArticleDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, url)
                    putString(ARG_URL, title)
                }
            }
        }
    }


}