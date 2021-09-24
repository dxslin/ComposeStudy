package com.slin.splayandroid.ui.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.slin.core.logger.logd
import com.slin.splayandroid.data.bean.ArticleBean
import com.slin.splayandroid.ext.collectCacheLazyPagingItems
import com.slin.splayandroid.ui.home.vm.HomeViewModel
import com.slin.splayandroid.widget.PageList

/**
 * author: slin
 * date: 2021/8/30
 * description: 广场
 *
 */
@Composable
fun PiazzaPanel(onItemClick: (ArticleBean) -> Unit) {

//    val items = homeViewModel.piazzaDataFlow.collectAsLazyPagingItems()

    val homeViewModel: HomeViewModel = hiltViewModel()
    val items = homeViewModel.collectCacheLazyPagingItems(flow = homeViewModel.piazzaDataFlow)


    logd { "PiazzaPanel: $homeViewModel" }

//    val testViewModel: HomeViewModel = hiltViewModel()
//    val items = testViewModel.collectCacheLazyPagingItems(flow = testViewModel.piazzaDataFlow)

    PageList(items = items) { _, article ->
        ArticleItem(articleBean = article, onItemClick = onItemClick)
    }

}