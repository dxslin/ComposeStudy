package com.slin.splayandroid.ui.test

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.LocalAbsoluteElevation
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.slin.splayandroid.data.bean.ArticleBean
import com.slin.splayandroid.ext.collectCacheLazyPagingItems
import com.slin.splayandroid.ui.home.ArticleItem
import com.slin.splayandroid.widget.PageList

/**
 * author: slin
 * date: 2021/9/17
 * description:
 *
 */

@Composable
fun TestScreen(
    onItemClick: (ArticleBean) -> Unit
) {

    val contentColor = LocalContentColor.current
    val absoluteElevation = LocalAbsoluteElevation.current + 1.dp

    CompositionLocalProvider(
//        LocalContentColor provides contentColor,
//        LocalAbsoluteElevation provides absoluteElevation
    ) {

        val testViewModel: TestViewModel = hiltViewModel()
//    val items = testViewModel.testArticleFlow.collectAsLazyPagingItems()
        val items = testViewModel.collectCacheLazyPagingItems(flow = testViewModel.testArticleFlow)

//    val homeViewModel:HomeViewModel = hiltViewModel()
//    val items = homeViewModel.collectCacheLazyPagingItems(flow = homeViewModel.homeArticleFlow)
        val listState = rememberLazyListState()

        PageList(items = items, listState = listState) { _, article ->
            ArticleItem(articleBean = article, onItemClick = onItemClick)
        }
    }

//    LazyColumn(
//        modifier = Modifier.fillMaxWidth(),
//        state = listState
//    ) {
//
//        logd { "firstVisibleItemIndex=${listState.firstVisibleItemIndex} itemCount=${items.itemCount}" }
//
//
//        item { Text(text = "Header") }
//        itemsIndexed(items) { _, item ->
//            item?.let {
//                ArticleItem(articleBean = item, onItemClick = onItemClick)
//            }
//        }
//    }


}