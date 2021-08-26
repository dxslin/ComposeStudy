package com.slin.splayandroid.ui.home

import androidx.compose.runtime.Composable
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

    @Composable
    override fun ComposeFragment.Content() {
        HomeScreen()
    }

}