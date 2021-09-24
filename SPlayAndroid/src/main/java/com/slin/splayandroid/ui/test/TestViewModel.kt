package com.slin.splayandroid.ui.test

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.slin.core.logger.logd
import com.slin.splayandroid.SPlayAndroid
import com.slin.splayandroid.base.BaseViewModel
import com.slin.splayandroid.base.EmptyViewState
import com.slin.splayandroid.base.ViewState
import com.slin.splayandroid.data.bean.ArticleBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * author: slin
 * date: 2021/9/17
 * description:
 *
 */

@HiltViewModel
class TestViewModel @Inject constructor() : BaseViewModel<ViewState>(EmptyViewState) {

    private val testRepository: TestRepository = SPlayAndroid.component.testRepository()

    init {
        viewModelScope.launch { }
    }

    //首页列表状态
    val testLazyListState: LazyListState = LazyListState()

    /**
     * 首页文章
     */
    val testArticleFlow: Flow<PagingData<ArticleBean>> =
        testRepository.getHomeArticles(null).cachedIn(viewModelScope)

    override fun onCleared() {
        super.onCleared()
        logd { "onClear" }
    }

}