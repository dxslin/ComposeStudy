package com.slin.splayandroid.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.slin.splayandroid.base.BaseFragment

/**
 * author: slin
 * date: 2021/8/23
 * description:
 *
 */
class HomeFragment : BaseFragment() {


    @Composable
    override fun ComposeView.ContentView() {
        HomePage()
    }

}