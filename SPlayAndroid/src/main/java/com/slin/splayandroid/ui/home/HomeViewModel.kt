package com.slin.splayandroid.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    val count: MutableLiveData<Int> = MutableLiveData(0)

    val name: MutableLiveData<String> = MutableLiveData("slin")

//    val bannerLiveData:LiveData<List<BannerBean>> = liveData {
//        emit(homeRepository.getBanner())
//    }

    val bannerFlow: StateFlow<List<BannerBean>> = stateFlow(listOf()) {
        emit(homeRepository.getBanner())
    }


}