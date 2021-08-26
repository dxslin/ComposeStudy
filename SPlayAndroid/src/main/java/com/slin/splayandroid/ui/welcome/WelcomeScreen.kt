package com.slin.splayandroid.ui.welcome

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.slin.splayandroid.R
import com.slin.splayandroid.widget.NetworkImage

/**
 * author: slin
 * date: 2021/8/10
 * description:
 *
 */
@Preview()
@Composable
fun WelcomeScreen() {
    val context = LocalContext.current
    val bottomBoxHeight = 200.dp

    val welcomeViewModel: WelcomeViewModel = viewModel()

    val adImageUrl by welcomeViewModel.adImageUrl.collectAsState()
    val countDown by welcomeViewModel.countDown.collectAsState()


    Box(modifier = Modifier) {
        Image(
            painter = painterResource(id = R.drawable.img_splay_android_bg),
            contentDescription = "WindowBackground",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        NetworkImage(
            url = adImageUrl,
            contentDescription = "ad",
            modifier = Modifier
                .padding(bottom = bottomBoxHeight)
                .fillMaxSize()
                .align(Alignment.TopCenter),
            contentScale = ContentScale.FillBounds
        )

        Box(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = bottomBoxHeight + 48.dp + 20.dp)
                .fillMaxWidth()
                .height(48.dp)
                .background(
                    MaterialTheme.colors.surface.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(24.dp)
                )
                .clickable {
                    val intent = Intent("android.intent.action.VIEW")
                    intent.data = Uri.parse("http://www.baidu.com")
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                }
                .align(Alignment.BottomCenter),
        ) {
            Text(
                text = "点击跳转详情页面",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.body1,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(bottomBoxHeight)
                .align(Alignment.BottomCenter)
        ) {

//                Text(
//                    text = stringResource(id = R.string.app_name),
//                    style = MaterialTheme.typography.h5,
//                    modifier = Modifier.align(Alignment.Center)
//                )

            Button(
                onClick = { welcomeViewModel.exitCountDown() },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .align(Alignment.CenterEnd),
                shape = RoundedCornerShape(50),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Text(
                    text = "跳过 $countDown",
                    modifier = Modifier
                )
            }

        }

    }

}