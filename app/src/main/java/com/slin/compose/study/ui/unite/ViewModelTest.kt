package com.slin.compose.study.ui.unite

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.slin.compose.study.ui.samples.LayoutItem
import com.slin.compose.study.ui.samples.MultiTestPage
import com.slin.compose.study.ui.samples.samples
import com.slin.compose.study.ui.theme.ComposeStudyTheme
import com.slin.compose.study.ui.theme.Size
import com.slin.compose.study.ui.unite.viewmodel.HomeViewModel
import com.slin.compose.study.ui.unite.viewmodel.UserViewModel
import com.slin.compose.study.ui.unite.viewmodel.initSamples
import com.slin.core.logger.logd

/**
 * author: slin
 * date: 2021/5/25
 * description:
 *
 */

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Preview
@Composable
fun ViewModelTest() {
    val testItems = listOf(
        LayoutItem("ViewModel") { UserModel() },
        LayoutItem("ShowViewModel") { ShowViewModel() },
        LayoutItem("RememberListBug") { RememberListBug() },
    )
    MultiTestPage(title = "ViewModelTest", testItems = testItems)
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun UserModel() {
    val userViewModel: UserViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()
    logd { "UserModel: homeViewModel: $homeViewModel" }

    //如果需要使用observeAsState方法，需要导入compose-runtime/runtime-livedata
    val userName by userViewModel.userName.observeAsState("empty")
    val samplePages by homeViewModel.samples.observeAsState()

    val sampleVersion by homeViewModel.samplesVersion.observeAsState()

    Column {

        TextField(value = userName, onValueChange = { text ->
            userViewModel.userName.value = text
        }, modifier = Modifier.padding(top = Size.small))

        Row(modifier = Modifier.padding(top = Size.medium)) {
            Button(
                onClick = { homeViewModel.updateSamples(samples) },
            ) {
                Text(text = "Update")
            }
            Button(
                onClick = { homeViewModel.addSamples() },
                modifier = Modifier.padding(start = Size.small)
            ) {
                Text(text = "Add Sample")
            }
            Button(
                onClick = { samplePages?.addAll(initSamples) },
                modifier = Modifier.padding(start = Size.small)
            ) {
                Text(text = "Add State")
            }
        }
        Text(text = "samples version: $sampleVersion")

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
            samplePages?.forEach {
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
    logd { "ShowViewModel: HomeViewModel: $homeViewModel" }

    //如果需要使用observeAsState方法，需要导入compose-livedata
    val userName by userViewModel.userName.observeAsState("empty")
    val samplePages by homeViewModel.samples.observeAsState()
    logd { "ShowViewModel: samplePages: $samplePages" }

    Column {
        Text(text = "User: $userName")

        Text(
            text = "Samples",
            modifier = Modifier.padding(top = Size.medium),
            style = ComposeStudyTheme.typography.subtitle1
        )
        Column {
            logd { "ShowViewModel: samples update" }
            samplePages?.forEach {
                Text(text = it.name)
            }
        }
    }
}

@Composable
fun RememberListBug() {
    val names = mutableListOf(1, 2, 3)
    var version = 0
    val remNames by remember(version) { mutableStateOf(names) }

    Column {
        Row {
            Button(
                onClick = {
                    remNames.add(names.last() + 1)
                    version++

                },
            ) {
                Text(text = "Add")
            }
//            Button(
//                onClick = { homeViewModel.addSamples() },
//                modifier = Modifier.padding(start = Size.small)
//            ) {
//                Text(text = "Add Sample")
//            }
        }
        Column {
            remNames.forEach { item ->
                Text(text = "col $item")
            }
        }
    }

}

