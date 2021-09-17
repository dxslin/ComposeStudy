package com.slin.splayandroid.ui.home

import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.slin.splayandroid.data.bean.ArticleBean
import com.slin.splayandroid.ui.home.vm.HomeViewModel
import com.slin.splayandroid.widget.PageList

/**
 * author: slin
 * date: 2021/8/30
 * description: 每日一问
 *
 */
@Composable
fun DailyQuestionPanel(homeViewModel: HomeViewModel, onItemClick: (ArticleBean) -> Unit) {

    val lazyPagingItems = homeViewModel.dailyQuestionFlow.collectAsLazyPagingItems()

    PageList(items = lazyPagingItems) { _, article ->
        ArticleItem(articleBean = article, onItemClick = onItemClick)
    }

}