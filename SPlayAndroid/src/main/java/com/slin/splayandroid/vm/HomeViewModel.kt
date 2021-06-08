package com.slin.splayandroid.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slin.core.logger.logd

/**
 * author: slin
 * date: 2021/6/8
 * description:
 *
 */
class HomeViewModel : ViewModel() {

    val count: MutableLiveData<Int> = MutableLiveData(0)

    val name: MutableLiveData<String> = MutableLiveData("slin")

    init {
        name.observeForever {
            logd { "init name: $name" }
        }
    }

}