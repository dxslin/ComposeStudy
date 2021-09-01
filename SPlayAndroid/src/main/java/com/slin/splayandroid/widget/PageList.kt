package com.slin.splayandroid.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
    lazyPagingItems: LazyPagingItems<T>,
    headerContent: @Composable () -> Unit = {},
    emptyItemContent: @Composable (index: Int) -> Unit = {},
    itemContent: @Composable (index: Int, item: T) -> Unit,
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = true)
    SwipeRefresh(
        modifier = modifier,
        state = swipeRefreshState,
        onRefresh = { lazyPagingItems.refresh() }) {
        swipeRefreshState.isRefreshing = lazyPagingItems.loadState.refresh is LoadState.Loading

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            // header
            item { headerContent() }

            // content
            for (i in 0 until lazyPagingItems.itemCount) {
                item {
                    val item = lazyPagingItems[i]
                    if (item == null) {
                        emptyItemContent(i)
                    } else {
                        itemContent(i, item)
                    }
                }
            }

            // load state
            lazyPagingItems.loadState.let { loadState ->
                when {
                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem() }
                    }
                    loadState.append is LoadState.Error -> {
                        item { ErrorItem { lazyPagingItems.retry() } }
                    }
                    loadState.refresh is LoadState.Error -> {
                        if (lazyPagingItems.itemCount <= 0) {
                            item { EmptyContent { lazyPagingItems.retry() } }
                        } else {
                            item { ErrorItem { lazyPagingItems.retry() } }
                        }
                    }

                }

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
    ) {
        Text(
            text = stringResource(id = R.string.retry),
            modifier = Modifier
                .padding(start = 16.dp)
                .clickable { retry() },
            style = MaterialTheme.typography.body2,
        )
    }
}

@Composable
fun EmptyContent(retry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
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
