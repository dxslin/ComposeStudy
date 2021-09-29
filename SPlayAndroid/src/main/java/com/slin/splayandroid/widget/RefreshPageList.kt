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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.slin.splayandroid.R
import kotlinx.coroutines.delay

/**
 * author: slin
 * date: 2021/8/31
 * description:
 *
 */

/**
 * 可下拉刷新列表
 *
 * 加载布局什么的都在[LazyColumn]中，当做item处理。
 *
 * 多次测试Paging之后，发现每次进来第一次[items.loadState.refresh]总为[LoadState.NotLoading]并且
 * [items.itemCount]为0，因此第一次不绘制空数据布局，不然进来时会出现一次[Empty]闪烁。
 *
 * 该List当数据重新加载，数据返回出现了一次空，且这时绘制了其他item，比如Header或者加载布局，就会导致[LazyColumn]的
 * 状态丢失，无法回到之前的滚动位置。
 *
 *
 *
 */
@Composable
fun <T : Any> RefreshPageList(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<T>,
    listState: LazyListState = rememberLazyListState(),
    headerContent: @Composable (() -> Unit) = { },
    nullItemContent: @Composable (index: Int) -> Unit = {},
    itemContent: @Composable (index: Int, item: T) -> Unit,
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    // 控制第一次进来时不显示空数据布局，加载之后如果数据依然为空才显示
    var showEmptyView by remember(items) { mutableStateOf(false) }

    SwipeRefresh(
        modifier = modifier.fillMaxSize(),
        state = swipeRefreshState,
        onRefresh = { items.refresh() }) {

//        logd { "refresh = ${items.loadState.refresh} ${items.loadState.append}  itemCount = ${items.itemCount}  $showEmptyView" }

        if (items.loadState.refresh !is LoadState.Loading) {
            swipeRefreshState.CancelRefreshDelay()
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {

            // 如果第一次不绘制Header可以避免items改变时，itemCount为0，然后绘制了LazyColumn，导致滚动状态丢失问题
            if (showEmptyView) {
                // header
                item(key = "header") { headerContent() }
            }

            when (items.loadState.refresh) {
                is LoadState.NotLoading -> {
                    if (items.itemCount <= 0) {
                        if (showEmptyView) {
                            item(key = "refresh_empty") {
                                Empty(modifier = Modifier.fillMaxSize()) { items.refresh() }
                            }
                        } else {
                            showEmptyView = true
                        }
                    }
                }
                is LoadState.Error -> item(key = "refresh_error") {
                    Error(modifier = Modifier.fillMaxSize()) { items.refresh() }
                }

                is LoadState.Loading -> item(key = "refresh_loading") {
                    Loading(modifier = Modifier.fillMaxSize())
                }
            }

            itemsIndexed(items) { index, item ->
                if (item == null) {
                    nullItemContent(index)
                } else {
                    itemContent(index, item)
                }
            }

            // load state
            when (items.loadState.append) {
                is LoadState.Loading -> {
                    item(key = "append_loading") { Loading() }
                }
                is LoadState.Error -> {
                    item(key = "append_error") { Error { items.retry() } }
                }
                is LoadState.NotLoading -> {
                }
            }

        }
    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircularProgressIndicator()
        Text(
            text = stringResource(id = R.string.loading),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun Error(modifier: Modifier = Modifier, retry: () -> Unit) {
    Row(
        modifier = modifier
            .clickable { retry() }
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.retry),
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.body2,
        )
    }
}

//@Preview
//@Composable
//fun PreviewEmpty() {
//    Empty(Modifier.fillMaxSize()) {
//
//    }
//}

@Composable
fun Empty(modifier: Modifier = Modifier, retry: () -> Unit) {
    Column(
        modifier = modifier
            .clickable { retry() }
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.ic_empty),
            contentDescription = "Empty")
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.no_data),
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

/**
 * 延迟关闭刷新，防止刷新动画出来一下就消失了
 */
@Composable
private fun SwipeRefreshState.CancelRefreshDelay(delayMills: Long = 2000) {
    LaunchedEffect(Unit) {
        delay(delayMills)
        isRefreshing = false
    }
}


/**
 * 可下拉刷新列表
 *
 * 当数据为空的时候，不显示[LazyColumn]，直接显示Header和加载状态布局；数据不为空的时候，将Header和数据项显示到
 * [LazyColumn]里面。
 *
 */
@Composable
fun <T : Any> RefreshPageList2(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<T>,
    listState: LazyListState = rememberLazyListState(),
    headerContent: @Composable () -> Unit = {},
    nullItemContent: @Composable (index: Int) -> Unit = {},
    itemContent: @Composable (index: Int, item: T) -> Unit,
) {
    val refreshState = rememberSwipeRefreshState(isRefreshing = false)
    // 控制第一次进来时不显示空数据布局，加载之后如果数据依然为空才显示
    var showNullView = false

    SwipeRefresh(
        modifier = modifier.fillMaxSize(),
        state = refreshState,
        onRefresh = { items.refresh() }) {

        Column(modifier = Modifier.fillMaxSize()) {
            if (items.itemCount > 0) {
                LazyList(listState = listState,
                    items = items,
                    headerContent = headerContent,
                    nullItemContent = nullItemContent,
                    itemContent = itemContent)
            } else {
                headerContent()
                when (items.loadState.refresh) {
                    is LoadState.Loading -> Loading(modifier = Modifier.fillMaxSize())
                    is LoadState.Error -> {
                        refreshState.CancelRefreshDelay()
                        Error(modifier = Modifier.fillMaxSize()) {
                            items.retry()
                        }
                    }
                    is LoadState.NotLoading -> {
                        refreshState.CancelRefreshDelay()
                        if (showNullView) {
                            Empty(modifier = Modifier.fillMaxSize()) {
                                items.retry()
                            }
                        } else {
                            showNullView = true
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun <T : Any> LazyList(
    listState: LazyListState,
    items: LazyPagingItems<T>,
    headerContent: @Composable () -> Unit = {},
    nullItemContent: @Composable (index: Int) -> Unit = {},
    itemContent: @Composable (index: Int, item: T) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {

        // header
        item(key = "header") { headerContent() }

        itemsIndexed(items) { index, item ->
            if (item == null) {
                nullItemContent(index)
            } else {
                itemContent(index, item)
            }
        }

        when (items.loadState.refresh) {
            is LoadState.NotLoading -> item(key = "no more") { Text(text = "再拉也没有更多了") }
            is LoadState.Error -> item(key = "refresh_error") { Error(modifier = Modifier.fillMaxSize()) { items.retry() } }
            is LoadState.Loading -> item(key = "refresh_loading") { Loading(modifier = Modifier.fillMaxSize()) }

        }


    }
}
