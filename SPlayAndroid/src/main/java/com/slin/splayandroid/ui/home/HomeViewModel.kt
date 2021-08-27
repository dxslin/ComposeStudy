package com.slin.splayandroid.ui.home

import androidx.lifecycle.MutableLiveData
import com.slin.splayandroid.base.BaseViewModel
import com.slin.splayandroid.base.ViewState
import com.slin.splayandroid.data.bean.BannerBean
import com.slin.splayandroid.ext.stateFlow
import com.slin.splayandroid.ui.home.ds.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    val count: MutableLiveData<Int> = MutableLiveData(0)

    val name: MutableLiveData<String> = MutableLiveData("slin")

//    val bannerLiveData:LiveData<List<BannerBean>> = liveData {
//        emit(homeRepository.getBanner())
//    }

    val bannerFlow: StateFlow<List<BannerBean>> = stateFlow(listOf()) {
        emit(homeRepository.getBanner())
    }


}