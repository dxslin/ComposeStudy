package com.slin.splayandroid.ui.home.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.slin.core.logger.logd
import com.slin.splayandroid.base.BaseViewModel
import com.slin.splayandroid.base.EmptyViewState
import com.slin.splayandroid.data.bean.ArticleBean
import com.slin.splayandroid.ui.home.ds.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * author: slin
 * date: 2021/6/8
 * description:
 *
 */

@HiltViewModel
class PiazzaViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    BaseViewModel<EmptyViewState>(EmptyViewState) {

    /**
     * 广场
     */
    val piazzaDataFlow: Flow<PagingData<ArticleBean>> =
        homeRepository.getPiazzaList().cachedIn(viewModelScope)

    override fun onCleared() {
        super.onCleared()

        logd { "onCleared" }
    }

}