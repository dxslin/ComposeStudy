package com.slin.compose.study.ui.unite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.slin.compose.study.R
import com.slin.compose.study.ui.NavDestinations
import com.slin.compose.study.ui.samples.LayoutSample
import com.slin.compose.study.ui.samples.SamplePage
import com.slin.core.logger.logd

/**
 * author: slin
 * date: 2021/5/25
 * description:
 *
 */

val initSamples = mutableListOf(
    SamplePage(
        "Init",
        R.drawable.img_cartoon_1,
        NavDestinations.ROUTE_LAYOUT
    ) { LayoutSample() },
)

class HomeViewModel : ViewModel() {

    private val _samples: MutableLiveData<MutableList<SamplePage>> = MutableLiveData(ArrayList())
    val samples: LiveData<MutableList<SamplePage>> = _samples
    val samplesVersion: MutableLiveData<Int> = MutableLiveData(0)
    val msgs = samples.asFlow()

    init {
        _samples.value = MutableList(initSamples.size) { index -> initSamples[index] }
        _samples.observeForever { logd { "_samples size : ${it.size}" } }
        samples.observeForever {
            samplesVersion.value = samplesVersion.value?.plus(1)
            logd { "samples size : ${it.size}" }
        }

    }

    fun updateSamples(pages: List<SamplePage>) {
        val mutablePages = MutableList(pages.size) { index -> pages[index] }
        _samples.value = mutablePages

    }

    fun addSamples() {
        _samples.value?.addAll(initSamples)
        _samples.value = _samples.value
    }

}