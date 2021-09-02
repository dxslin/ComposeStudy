package com.slin.splayandroid.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.Composable
import androidx.navigation.fragment.findNavController
import com.slin.core.logger.logd
import com.slin.splayandroid.base.ComposeFragment
import com.slin.splayandroid.nav.Screen
import com.slin.splayandroid.nav.navigate
import com.slin.splayandroid.ui.detail.ArticleDetailFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * author: slin
 * date: 2021/8/23
 * description:
 *
 */
@AndroidEntryPoint
class HomeFragment : ComposeFragment() {

//    private val homeViewModel: HomeViewModel by viewModels()

    @Composable
    override fun ComposeFragment.Content() {
        HomeScreen(onItemClick = { article ->
            val bundle = Bundle().apply {
                putString(ArticleDetailFragment.ARG_TITLE, article.title)
                putString(ArticleDetailFragment.ARG_URL, article.link)
            }
            navigate(Screen.ArticleDetail(bundle))
        })
        findNavController().setOnBackPressedDispatcher(OnBackPressedDispatcher {
            logd { "" }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        lifecycleScope.launch {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                homeViewModel.bannerFlow.collect {
//                    log { "banner: ${it.joinToString()}" }
//                }
//            }
//        }
    }

}