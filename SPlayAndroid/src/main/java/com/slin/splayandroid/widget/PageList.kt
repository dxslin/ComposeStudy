package com.slin.splayandroid.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.slin.splayandroid.R

/**
 * author: slin
 * date: 2021/8/31
 * description:
 *
 */

@Composable
fun <T : Any> PageList(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<T>,
    listState: LazyListState = rememberLazyListState(),
    headerContent: @Composable () -> Unit = {},
    nullItemContent: @Composable (index: Int) -> Unit = {},
    itemContent: @Composable (index: Int, item: T) -> Unit,
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    SwipeRefresh(
        modifier = modifier,
        state = swipeRefreshState,
        onRefresh = { items.refresh() }) {

        swipeRefreshState.isRefreshing = items.loadState.refresh is LoadState.Loading

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            state = listState
        ) {

            // 如果使用item在LazyColumn里面布局，那么跳转页面后列表滑动位置丢失，因此暂时将头部放置到item0中
            // header
//            item(key = -555) { Text(text = "Head") }

            itemsIndexed(items) { index, item ->
//                if (index == 0) {
//                    headerContent()
//                }
                if (item == null) {
                    nullItemContent(index)
                } else {
                    itemContent(index, item)
                }
            }

            // load state
            when (items.loadState.append) {
                is LoadState.Loading -> {
                    item(key = "append_loading") { LoadingItem() }
                }
                is LoadState.Error -> {
                    item(key = "append_error") { ErrorItem { items.retry() } }
                }
                is LoadState.NotLoading -> {
                }
            }

            when (items.loadState.refresh) {
                is LoadState.NotLoading -> {
//                    if (lazyPagingItems.itemCount <= 0) {
//                        item(key = "refresh_empty") { EmptyContent { lazyPagingItems.retry() } }
//                    }
                }
                is LoadState.Error -> item(key = "refresh_error") { ErrorItem { items.retry() } }

                is LoadState.Loading -> item(key = "refresh_loading") { LoadingItem() }

            }


        }
    }
}

@Composable
fun LoadingItem() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp)
    ) {
        CircularProgressIndicator()
        Text(
            text = stringResource(id = R.string.loading),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun ErrorItem(retry: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp)
            .clickable { retry() }
    ) {
        Text(
            text = stringResource(id = R.string.retry),
            modifier = Modifier
                .padding(start = 16.dp),
            style = MaterialTheme.typography.body2,
        )
    }
}

@Composable
fun EmptyContent(retry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { retry() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_empty), contentDescription = "Empty")
        Text(
            text = stringResource(id = R.string.no_data),
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}
