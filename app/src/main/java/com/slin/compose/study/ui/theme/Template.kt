package com.slin.compose.study.ui.theme

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slin.compose.study.R
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import dev.chrisbanes.accompanist.insets.statusBarsPadding


/**
 * author: slin
 * date: 2021/3/12
 * description:
 *
 */

@Preview
@Composable
fun CsAppBar(
    isShowBack: Boolean = false,
    title: String = stringResource(id = R.string.app_name),
    actions: @Composable RowScope.() -> Unit = {},
) {
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current.onBackPressedDispatcher
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = if (isShowBack) {
            {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = "back",
                    modifier = Modifier.clickable {
                        backPressedDispatcher.onBackPressed()
                    }
                )
            }
        } else {
            null
        },
        actions = actions
    )
}
