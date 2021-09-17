package com.slin.splayandroid.ui.test

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.itemsIndexed
import com.slin.core.logger.logd
import com.slin.splayandroid.data.bean.ArticleBean
import com.slin.splayandroid.ext.collectCacheLazyPagingItems
import com.slin.splayandroid.ui.home.ArticleItem

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
    val testViewModel: TestViewModel = viewModel()
//    val items = testViewModel.testArticleFlow.collectAsLazyPagingItems()
    val items = testViewModel.collectCacheLazyPagingItems(flow = testViewModel.testArticleFlow)
    val listState = rememberLazyListState()



    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        state = listState
    ) {

        logd { "firstVisibleItemIndex=${listState.firstVisibleItemIndex} itemCount=${items.itemCount}" }


        item { Text(text = "Header") }
        itemsIndexed(items) { _, item ->
            item?.let {
                ArticleItem(articleBean = item, onItemClick = onItemClick)
            }
        }
    }


}