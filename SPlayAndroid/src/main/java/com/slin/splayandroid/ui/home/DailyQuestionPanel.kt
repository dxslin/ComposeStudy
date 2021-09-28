package com.slin.splayandroid.ui.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.slin.splayandroid.data.bean.ArticleBean
import com.slin.splayandroid.ui.home.vm.DailyQuestionViewModel
import com.slin.splayandroid.widget.RefreshPageList

/**
 * author: slin
 * date: 2021/8/30
 * description: 每日一问
 *
 */
@Composable
fun DailyQuestionPanel(onItemClick: (ArticleBean) -> Unit) {
    val dailyQuestionViewModel: DailyQuestionViewModel = hiltViewModel()
    val lazyPagingItems = dailyQuestionViewModel.dailyQuestionFlow.collectAsLazyPagingItems()
//    val lazyPagingItems =
//        dailyQuestionViewModel.collectCacheLazyPagingItems(flow = dailyQuestionViewModel.dailyQuestionFlow)

    RefreshPageList(items = lazyPagingItems) { _, article ->
        ArticleItem(articleBean = article, onItemClick = onItemClick)
    }

}