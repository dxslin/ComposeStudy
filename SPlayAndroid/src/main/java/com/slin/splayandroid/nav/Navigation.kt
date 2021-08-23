package com.slin.splayandroid.nav

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.slin.splayandroid.R

/**
 * author: slin
 * date: 2021/8/23
 * description:
 *
 */

enum class Screen {
    Welcome,
    Home,
}

fun Fragment.navigate(to: Screen) {
    when (to) {
        Screen.Welcome -> findNavController().navigate(R.id.welcome_fragment)
        Screen.Home -> findNavController().navigate(R.id.home_fragment)
    }

}
