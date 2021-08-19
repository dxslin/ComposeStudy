package com.slin.splayandroid.ui.welcome

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * author: slin
 * date: 2021/8/19
 * description:
 *
 */
const val TestImageUrl =
    "https://images.unsplash.com/photo-1629206896310-68433de7ded1?auto=format&fit=crop&w=500&q=60"

class WelcomeViewModel : ViewModel() {

    val adImageUrl: StateFlow<String> = MutableStateFlow(TestImageUrl)


}