package com.slin.splayandroid.ui.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.slin.splayandroid.data.bean.ArticleBean
import com.slin.splayandroid.ext.collectCacheLazyPagingItems
import com.slin.splayandroid.ui.home.vm.PiazzaViewModel
import com.slin.splayandroid.widget.PageList

/**
 * author: slin
 * date: 2021/8/30
 * description: 广场
 *
 */
@Composable
fun PiazzaPanel(onItemClick: (ArticleBean) -> Unit) {


    val piazzaViewModel: PiazzaViewModel = hiltViewModel()
    val items = piazzaViewModel.collectCacheLazyPagingItems(flow = piazzaViewModel.piazzaDataFlow)
//    val items = piazzaViewModel.piazzaDataFlow.collectAsLazyPagingItems()

    PageList(items = items) { _, article ->
        ArticleItem(articleBean = article, onItemClick = onItemClick)
    }

}