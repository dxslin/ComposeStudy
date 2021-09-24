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
 * description: 每日一问
 *
 */
@Composable
fun DailyQuestionPanel(onItemClick: (ArticleBean) -> Unit) {
    val homeViewModel: HomeViewModel = hiltViewModel()
//    val lazyPagingItems = homeViewModel.dailyQuestionFlow.collectAsLazyPagingItems()
    val lazyPagingItems =
        homeViewModel.collectCacheLazyPagingItems(flow = homeViewModel.dailyQuestionFlow)

    logd { "DailyQuestionPanel:  $homeViewModel" }

    PageList(items = lazyPagingItems) { _, article ->
        ArticleItem(articleBean = article, onItemClick = onItemClick)
    }

}