package com.slin.splayandroid.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * author: slin
 * date: 2021/8/27
 * description:
 *
 */

interface ViewState


open class BaseViewModel<VS : ViewState>(initialViewState: VS) : ViewModel() {

    protected val viewState: MutableStateFlow<VS> = MutableStateFlow(initialViewState)

    private val stateCache: MutableMap<Any, Any> = hashMapOf()

    @Suppress("UNCHECKED_CAST")
    fun <T> getStateCache(key: Any): T? {
        stateCache.mapNotNull { }
        return stateCache[key] as T?
    }

    fun <T> getStateCacheNotNull(key: Any, default: T): T {
        return getStateCache<T>(key) ?: default
    }

    fun putStateCache(key: Any, value: Any) {
        stateCache[key] = value
    }

}