package com.slin.compose.study.ui.samples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.slin.compose.study.ui.theme.*


/**
 * author: slin
 * date: 2021/3/12
 * description:
 *
 */

@Preview
@Composable
fun ThemeSample() {

    val checkedState = remember { mutableStateOf(true) }

    if (checkedState.value) {
        PinkTheme {
            SampleContent(checkedState = checkedState)
        }
    } else {
        BlueTheme {
            SampleContent(checkedState = checkedState)
        }
    }


}

@Composable
fun SampleContent(checkedState: MutableState<Boolean>) {
    Scaffold(topBar = { CsAppBar(isShowBack = true, "ThemeSample") }, backgroundColor = ComposeStudyTheme.colors.primary) {
        Column(modifier = Modifier.padding(ComposeStudyTheme.paddings.medium)) {
            Text(
                text = "主题切换", style = ComposeStudyTheme.typography.h5,
                modifier = Modifier
            )

            Switch(
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                },
                modifier = Modifier.padding(top = Size.small)
            )
        }

    }
}


