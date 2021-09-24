package com.slin.splayandroid.ext

import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.slin.splayandroid.base.BaseViewModel
import com.slin.splayandroid.base.ViewState
import kotlinx.coroutines.flow.Flow

/**
 * author: slin
 * date: 2021/9/13
 * description:
 *
 */


@Composable
inline fun <reified T : Any> BaseViewModel<out ViewState>.collectCacheLazyPagingItems(flow: Flow<PagingData<T>>): LazyPagingItems<T> {
    return getStateCache(flow) ?: flow.collectAsLazyPagingItems()
        .apply {
            putStateCache(flow, this)
        }
}
