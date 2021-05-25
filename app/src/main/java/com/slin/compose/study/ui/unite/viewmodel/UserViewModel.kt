package com.slin.compose.study.ui.unite.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * author: slin
 * date: 2021/5/25
 * description:
 *
 */
class UserViewModel : ViewModel() {

    val userName: MutableLiveData<String> = MutableLiveData("")


}