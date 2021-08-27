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


}