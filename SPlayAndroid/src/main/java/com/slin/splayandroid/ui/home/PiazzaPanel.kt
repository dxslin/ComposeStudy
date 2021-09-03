package com.slin.splayandroid.ui.home

import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.slin.splayandroid.data.bean.ArticleBean
import com.slin.splayandroid.ui.home.vm.HomeViewModel
import com.slin.splayandroid.widget.PageList

/**
 * author: slin
 * date: 2021/8/30
 * description: 广场
 *
 */
@Composable
fun PiazzaPanel(homeViewModel: HomeViewModel, onItemClick: (ArticleBean) -> Unit) {

    val lazyPagingItems = homeViewModel.piazzaDataFlow.collectAsLazyPagingItems()

    PageList(lazyPagingItems = lazyPagingItems) { _, article ->
        ArticleItem(articleBean = article, onItemClick = onItemClick)
    }

}