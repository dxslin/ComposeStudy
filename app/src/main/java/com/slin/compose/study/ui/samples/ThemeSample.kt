package com.slin.compose.study.ui.samples

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.slin.compose.study.ui.theme.*
import com.slin.compose.study.weight.Spinner



/**
 * author: slin
 * date: 2021/3/12
 * description: 主题
 *
 */

val THEMES = listOf("default", "pink", "blue")

//@Preview
@Composable
fun ThemeSample() {

    val nightMode = remember { mutableStateOf(false) }
    val themeValue = remember { mutableStateOf(THEMES[0]) }

    if (nightMode.value) {
        SelectTheme(darkTheme = nightMode.value, themeValue = themeValue) {
            SampleContent(nightMode = nightMode, themeValue)
        }
    } else {
        SelectTheme(darkTheme = nightMode.value, themeValue = themeValue) {
            SampleContent(nightMode = nightMode, themeValue)
        }
    }


}

@Composable
private fun SelectTheme(
    darkTheme: Boolean,
    themeValue: MutableState<String>,
    content: @Composable () -> Unit
) {
    when (themeValue.value) {
        "pink" -> PinkTheme(darkTheme = darkTheme, content = content)
        "blue" -> BlueTheme(darkTheme = darkTheme, content = content)
        else -> ComposeStudyTheme(darkTheme = darkTheme, content = content)
    }
}

@Composable
private fun SampleContent(nightMode: MutableState<Boolean>, themeValue: MutableState<String>) {
    ScaffoldWithCsAppBar(
        title = "ThemeSample"
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(innerPadding)
                .padding(ComposeStudyTheme.paddings.medium)
        ) {
            Card {
                NightModeSwitch(nightMode = nightMode)
            }
            Card(modifier = Modifier.padding(top = Size.small)) {
                ThemeSpinner(themeValue = themeValue)
            }
        }

    }
}

@Composable
private fun NightModeSwitch(nightMode: MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .padding(ComposeStudyTheme.paddings.small)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Text(
            text = "夜间模式", style = ComposeStudyTheme.typography.h5,
            modifier = Modifier
                .weight(1f)
                .wrapContentSize(align = Alignment.CenterStart)
        )

        Switch(
            checked = nightMode.value,
            onCheckedChange = {
                nightMode.value = it
            },
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentSize(Alignment.CenterEnd)
        )
    }
}

@Composable
private fun ThemeSpinner(themeValue: MutableState<String>) {
    Row(
        modifier = Modifier
            .padding(ComposeStudyTheme.paddings.small)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Text(
            text = "主题切换", style = ComposeStudyTheme.typography.h5,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        Spinner(
            list = THEMES,
            modifier = Modifier
                .width(150.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .wrapContentHeight(Alignment.CenterVertically),
            onTextChange = {
                themeValue.value = it
            }
        )

    }

}

//@Preview
@Composable
private fun PreviewNightModeSwitch() {
    val nightMode = remember { mutableStateOf(false) }
    NightModeSwitch(nightMode = nightMode)
}

@Preview
@Composable
private fun PreviewThemeSpinner() {
    val themeValue = remember { mutableStateOf(THEMES[0]) }
    ThemeSpinner(themeValue = themeValue)
}

