package com.slin.compose.study.ui.unite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.slin.compose.study.ui.samples.LayoutItem
import com.slin.compose.study.ui.samples.MultiTestPage
import com.slin.compose.study.ui.theme.ComposeStudyTheme
import com.slin.compose.study.ui.theme.Size
import com.slin.compose.study.ui.unite.viewmodel.HomeViewModel
import com.slin.compose.study.ui.unite.viewmodel.UserViewModel

/**
 * author: slin
 * date: 2021/5/25
 * description:
 *
 */

@Preview
@Composable
fun ViewModelTest() {
    val testItems = listOf(
        LayoutItem("ViewModel") { UserModel() },
        LayoutItem("ShowViewModel") { ShowViewModel() },
    )
    MultiTestPage(title = "ViewModelTest", testItems = testItems)
}

@Preview
@Composable
fun UserModel() {
    val userViewModel: UserViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()

    //如果需要使用observeAsState方法，需要导入compose-runtime/runtime-livedata
    val userName by userViewModel.userName.observeAsState("empty")
    val samplePages by homeViewModel.samples.observeAsState(listOf())

    Column {

        TextField(value = userName, onValueChange = { text ->
            userViewModel.userName.value = text
        }, modifier = Modifier.padding(top = Size.small))

        Text(
            text = "Samples",
            modifier = Modifier
                .padding(top = Size.medium)
                .clickable {
                    userViewModel.userName.value = userName
                },
            style = ComposeStudyTheme.typography.subtitle1,
        )
        Column {
            samplePages.forEach {
                Text(text = it.name)
            }
        }
    }
}

@Preview
@Composable
fun ShowViewModel() {
    val userViewModel: UserViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()

    //如果需要使用observeAsState方法，需要导入compose-livedata
    val userName by userViewModel.userName.observeAsState("empty")
    val samplePages by homeViewModel.samples.observeAsState(listOf())

    Column {
        Text(text = "User: $userName")

        Text(
            text = "Samples",
            modifier = Modifier.padding(top = Size.medium),
            style = ComposeStudyTheme.typography.subtitle1
        )
        Column {
            samplePages.forEach {
                Text(text = it.name)
            }
        }
    }
}