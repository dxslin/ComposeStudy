package com.slin.splayandroid.ui.detail

import android.os.Bundle
import androidx.compose.runtime.Composable
import com.slin.splayandroid.base.ComposeFragment

/**
 * author: slin
 * date: 2021/9/1
 * description:
 *
 */


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
        ArticleDetailScreen(mTitle = mTitle, mUrl = mUrl)
    }


    companion object {

        const val ARG_TITLE = "title"
        const val ARG_URL = "url"

//        fun getInstance(url: String, title: String = ""): ArticleDetailFragment {
//            return ArticleDetailFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_TITLE, url)
//                    putString(ARG_URL, title)
//                }
//            }
//        }
    }


}