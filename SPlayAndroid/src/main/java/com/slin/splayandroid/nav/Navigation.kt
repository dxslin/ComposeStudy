package com.slin.splayandroid.nav

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.slin.splayandroid.R

/**
 * author: slin
 * date: 2021/8/23
 * description:
 *
 */

sealed class Screen {
    object Welcome : Screen()
    object Home : Screen()
    class ArticleDetail(val args: Bundle) : Screen()
}

fun Fragment.navigate(to: Screen) {
    when (to) {
        is Screen.Welcome -> findNavController().navigate(R.id.splash_fragment)
        is Screen.Home -> findNavController().navigate(
            R.id.home_fragment,
            null,
            NavOptions.Builder().setLaunchSingleTop(true).build()
        )
        is Screen.ArticleDetail -> findNavController().navigate(
            R.id.article_detail_fragment,
            to.args
        )
    }

}
