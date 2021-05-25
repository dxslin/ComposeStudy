package com.slin.compose.study.ui.unite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slin.compose.study.R
import com.slin.compose.study.ui.NavDestinations
import com.slin.compose.study.ui.samples.LayoutSample
import com.slin.compose.study.ui.samples.SamplePage

/**
 * author: slin
 * date: 2021/5/25
 * description:
 *
 */
class HomeViewModel : ViewModel() {

    private val _samples: MutableLiveData<List<SamplePage>> = MutableLiveData(ArrayList())
    val samples: LiveData<List<SamplePage>> = _samples

    init {
        _samples.value = listOf(
            SamplePage(
                "Layout",
                R.drawable.img_cartoon_1,
                NavDestinations.ROUTE_LAYOUT
            ) { LayoutSample() },
        )
    }

}