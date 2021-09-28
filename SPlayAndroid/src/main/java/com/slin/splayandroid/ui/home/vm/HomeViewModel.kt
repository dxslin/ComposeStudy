package com.slin.splayandroid.ui.home.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.slin.core.logger.logd
import com.slin.splayandroid.base.BaseViewModel
import com.slin.splayandroid.base.ViewState
import com.slin.splayandroid.data.bean.ArticleBean
import com.slin.splayandroid.data.bean.BannerBean
import com.slin.splayandroid.ext.stateFlow
import com.slin.splayandroid.ui.home.ds.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * author: slin
 * date: 2021/6/8
 * description:
 *
 */
data class HomeViewState(
    val loading: Boolean = false
) : ViewState

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    BaseViewModel<HomeViewState>(HomeViewState()) {

    /**
     * Banner
     */
    val bannerFlow: StateFlow<List<BannerBean>> = stateFlow(listOf()) {
        emit(homeRepository.getBanner())
    }

    /**
     * 首页文章
     */
    val homeArticleFlow: Flow<PagingData<ArticleBean>> =
        homeRepository.getHomeArticles(1000).cachedIn(viewModelScope)


    override fun onCleared() {
        super.onCleared()

        logd { "onCleared" }
    }

}