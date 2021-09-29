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
import com.slin.core.logger.logd
import com.slin.splayandroid.R
import kotlinx.coroutines.delay

/**
 * author: slin
 * date: 2021/8/31
 * description: 可刷新的数据列表显示
 *
 */

/**
 * [RefreshPageList]用到的状态变量
 */
private data class RefreshPageListState(
    /** 控制第一次进来时不显示空数据布局，加载之后如果数据依然为空才显示 */
    var showEmptyView: Boolean = false,

    /** 开始刷新的时间戳，防止刷新动画太快 */
    var startRefreshTime: Long = System.currentTimeMillis(),

    /** 记录上一次加载状态，如果上一次是错误的话，那么刷新的时候也不能显示列表数据 */
    var lastLoadState: LoadState = LoadState.NotLoading(false),
)


/**
 * 可下拉刷新列表
 *
 * 当数据为空的时候，不显示[LazyColumn]，直接显示Header和加载状态布局；数据不为空的时候，将Header和数据项显示到
 * [LazyColumn]里面。
 *
 * 不足：在[SwipeRefresh]中，不使用[LazyColumn]无法触发下拉加载；所以，当数据加载出现问题（加载失败或者数据为空)
 * 时，不能使用下拉重新加载，只能点击失败布局重新加载。
 *
 */
@Composable
fun <T : Any> RefreshPageList(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<T>,
    listState: LazyListState = rememberLazyListState(),
    headerContent: @Composable () -> Unit = {},
    nullItemContent: @Composable (index: Int) -> Unit = {},
    itemContent: @Composable (index: Int, item: T) -> Unit,
) {
    val refreshState = rememberSwipeRefreshState(isRefreshing = false)

    val refreshPageListState = RefreshPageListState()

    SwipeRefresh(
        modifier = modifier.fillMaxSize(),
        state = refreshState,
        onRefresh = {
            refreshState.isRefreshing = true
            refreshPageListState.startRefreshTime = System.currentTimeMillis()
            items.refresh()
        }) {

        logd { "refresh = ${items.loadState.refresh} ${items.loadState.append}  itemCount = ${items.itemCount} " }
        logd { "RefreshPageList2: $refreshPageListState  isRefreshing = ${refreshState.isRefreshing}" }

        if (refreshState.isRefreshing && items.loadState.refresh !is LoadState.Loading) {
            refreshState.CancelRefreshDelay(refreshPageListState.startRefreshTime)
        }

        if (items.itemCount <= 0) {
            LoadStateView(items = items,
                headerContent = headerContent,
                refreshPageListState = refreshPageListState)
        } else if (items.loadState.refresh !is LoadState.Error &&
            refreshPageListState.lastLoadState !is LoadState.Error
        ) {
            LazyList(listState = listState,
                items = items,
                headerContent = headerContent,
                nullItemContent = nullItemContent,
                itemContent = itemContent)
        } else {
            LoadStateView(items = items,
                headerContent = headerContent,
                refreshPageListState = refreshPageListState)
        }
        refreshPageListState.lastLoadState = items.loadState.refresh
    }
}

/**
 * 各种加载状态显示
 *
 */
@Composable
private fun <T : Any> LoadStateView(
    items: LazyPagingItems<T>,
    headerContent: @Composable () -> Unit = {},
    refreshPageListState: RefreshPageListState,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        headerContent()
        when (items.loadState.refresh) {
            is LoadState.Loading -> Loading(modifier = Modifier.fillMaxSize())
            is LoadState.Error -> Error(modifier = Modifier.fillMaxSize()) {
                items.refresh()
            }
            is LoadState.NotLoading ->
                // 第一次进来时不显示空数据布局，加载之后如果数据依然为空才显示
                if (refreshPageListState.showEmptyView) {
                    Empty(modifier = Modifier.fillMaxSize()) {
                        items.refresh()
                    }
                } else {
                    refreshPageListState.showEmptyView = true
                }
        }
    }
}

/**
 * 显示列表数据，只有当数据不为空时才会执行
 */
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
            is LoadState.NotLoading -> {
                //item(key = "no more") { Text(text = "再拉也没有更多了") }
            }
            is LoadState.Error -> item(key = "refresh_error") { Error(modifier = Modifier.fillMaxSize()) { items.retry() } }
            is LoadState.Loading -> item(key = "refresh_loading") { Loading(modifier = Modifier.fillMaxSize()) }

        }
    }
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
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
private fun Error(modifier: Modifier = Modifier, retry: () -> Unit) {
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
private fun Empty(modifier: Modifier = Modifier, retry: () -> Unit) {
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
 * 延迟关闭刷新，保证动画至少持续[minDelayMills]ms，防止刷新动画出来一下就消失
 *
 * @param startRefreshMills 开始刷新的时间
 * @param minDelayMills 动画最少执行时间
 */
@Composable
private fun SwipeRefreshState.CancelRefreshDelay(
    startRefreshMills: Long,
    minDelayMills: Long = 1500L,
) {
//    logd { "CancelRefreshDelay: $startRefreshMills $minDelayMills" }
    LaunchedEffect(Unit) {
        delay(startRefreshMills + minDelayMills - System.currentTimeMillis())
        isRefreshing = false
    }
}


/**
 * 可下拉刷新列表
 *
 * 加载布局什么的都在[LazyColumn]中，当做item处理。
 *
 * 多次测试Paging之后，发现每次进来第一次[items.loadState.refresh]总为[LoadState.NotLoading]并且
 * [items.itemCount]为0，因此第一次不绘制空数据布局，不然进来时会出现一次[Empty]闪烁。
 *
 * 该List当数据重新加载，数据返回出现了一次空，且这时绘制了其他item，比如Header或者加载布局，就会导致[LazyColumn]的
 * 状态丢失，无法回到之前的滚动位置。因此Header第一次进入不会绘制，需要等加载状态出来之后才绘制
 *
 * 不足：由于加载状态都是布局在[LazyColumn]的Item中，无法满屏居中显示加载动画，只能在最上面显示
 *
 * @note 暂时弃用
 *
 */
@Composable
fun <T : Any> RefreshPageList2(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<T>,
    listState: LazyListState = rememberLazyListState(),
    headerContent: @Composable (() -> Unit) = { },
    nullItemContent: @Composable (index: Int) -> Unit = {},
    itemContent: @Composable (index: Int, item: T) -> Unit,
) {
    val refreshState = rememberSwipeRefreshState(isRefreshing = false)

    // 控制第一次进来时不显示空数据布局，加载之后如果数据依然为空才显示
    var showEmptyView by remember(items) { mutableStateOf(false) }

    // 开始刷新的时间戳，防止刷新动画太快消失，造成闪烁
    var startRefreshTime = System.currentTimeMillis()

    var lastLoadState by remember { mutableStateOf(items.loadState.refresh) }

    SwipeRefresh(
        modifier = modifier.fillMaxSize(),
        state = refreshState,
        onRefresh = {
            // SwipeRefresh 默认是不会将RefreshState状态设置true的，需要我们自己设置
            refreshState.isRefreshing = true
            startRefreshTime = System.currentTimeMillis()
            items.refresh()
        }) {

        logd { "refresh = ${items.loadState.refresh} ${items.loadState.append}  itemCount = ${items.itemCount}  $showEmptyView ${refreshState.isRefreshing}" }

        if (refreshState.isRefreshing && items.loadState.refresh !is LoadState.Loading) {
            refreshState.CancelRefreshDelay(startRefreshTime)
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
                    if (items.itemCount <= 0) {
                        Loading(modifier = Modifier.fillMaxSize())
                    }
                }
            }

            if (items.loadState.refresh !is LoadState.Error && lastLoadState !is LoadState.Error) {
                itemsIndexed(items) { index, item ->
                    if (item == null) {
                        nullItemContent(index)
                    } else {
                        itemContent(index, item)
                    }
                }

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
            lastLoadState = items.loadState.refresh
        }
    }
}