package com.slin.splayandroid.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * author: slin
 * date: 2021/8/19
 * description:
 *
 */
const val TestImageUrl =
    "https://images.unsplash.com/photo-1629206896310-68433de7ded1?auto=format&fit=crop&w=500&q=60"

class WelcomeViewModel : @HiltViewModel ViewModel() {

    val adImageUrl: StateFlow<String> = MutableStateFlow(TestImageUrl)

    private val _countDown = MutableStateFlow(5)
    val countDown: StateFlow<Int> = _countDown

    private var countDownJob: Job? = null


    suspend fun startCountDown() {
        countDownJob = viewModelScope.launch {
            _countDown.emit(_countDown.value - 1)
            delay(1000)
        }
    }

    fun exitCountDown() {
        countDownJob?.cancel()
        _countDown.value = 0
    }


}