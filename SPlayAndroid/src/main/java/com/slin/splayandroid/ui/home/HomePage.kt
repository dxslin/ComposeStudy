package com.slin.splayandroid.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.slin.splayandroid.vm.HomeViewModel

/**
 * author: slin
 * date: 2021/6/8
 * description:
 *
 */

@Composable
fun HomePage() {
    val homeViewModel: HomeViewModel = viewModel()

    val name = homeViewModel.name.observeAsState()
    val count = homeViewModel.count.observeAsState()


    Column() {
        Text(text = "$name: $count")


    }


}