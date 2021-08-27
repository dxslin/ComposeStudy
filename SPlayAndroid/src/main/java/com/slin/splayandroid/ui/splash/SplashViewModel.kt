package com.slin.splayandroid.ui.splash

import androidx.lifecycle.viewModelScope
import com.slin.splayandroid.BuildConfig
import com.slin.splayandroid.base.BaseViewModel
import com.slin.splayandroid.base.ViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

/**
 * author: slin
 * date: 2021/8/19
 * description:
 *
 */
const val TestImageUrl =
    "https://images.unsplash.com/photo-1629206896310-68433de7ded1?auto=format&fit=crop&w=500&q=60"

val COUNT_DOWN_NUM = if (BuildConfig.DEBUG) 1 else 5

data class SplashViewState(
    val skip: Boolean = false
) : ViewState

class SplashViewModel : BaseViewModel<SplashViewState>(SplashViewState()) {

    /**
     * 广告图
     */
    val adImageUrl: StateFlow<String> = MutableStateFlow(TestImageUrl)

    /**
     * 倒计时
     */
    val countFlow: StateFlow<Int> = viewState
        .combine(
            flow = flow {
                var count = COUNT_DOWN_NUM
                while (count >= 0) {
                    emit(count--)
                    delay(1000L)
                }
            },
            transform = { viewState, count ->
                if (viewState.skip) {
                    0
                } else {
                    count
                }
            })
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = COUNT_DOWN_NUM
        )

    fun skipCountDown() {
        viewState.value = SplashViewState(skip = true)
    }

}