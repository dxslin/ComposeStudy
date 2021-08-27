package com.slin.splayandroid.ui.home

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import com.slin.splayandroid.base.ComposeFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * author: slin
 * date: 2021/8/23
 * description:
 *
 */
@AndroidEntryPoint
class HomeFragment : ComposeFragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    @Composable
    override fun ComposeFragment.Content() {
        HomeScreen()
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