package com.slin.splayandroid.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.Composable
import com.slin.splayandroid.R
import com.slin.splayandroid.base.ComposeFragment
import com.slin.splayandroid.ext.toast
import dagger.hilt.android.AndroidEntryPoint

/**
 * author: slin
 * date: 2021/8/23
 * description:
 *
 */
@AndroidEntryPoint
class HomeFragment : ComposeFragment() {

    private var lastBackMills = 0L

    private val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - lastBackMills < 1000) {
                    activity?.finish()
                } else {
                    lastBackMills = System.currentTimeMillis()
                    toast { requireContext().getString(R.string.exit_app) }
                }
            }
        }

    }

    @Composable
    override fun ComposeFragment.Content() {
//        HomeScreen(onItemClick = { article ->
//            val bundle = Bundle().apply {
//                putString(ArticleDetailFragment.ARG_TITLE, article.title)
//                putString(ArticleDetailFragment.ARG_URL, article.link)
//            }
//            navigate(Screen.ArticleDetail(bundle))
//        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

}