package com.slin.splayandroid.ui.welcome

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slin.splayandroid.R

/**
 * author: slin
 * date: 2021/8/10
 * description:
 *
 */
@Preview
@Composable
fun Welcome() {
    val context = LocalContext.current
    val bottomBoxHeight = 100.dp
    Box {
        Image(
            painter = painterResource(id = R.drawable.img_fate_arthur1),
            contentDescription = "ad",
            modifier = Modifier
                .padding(bottom = bottomBoxHeight)
                .fillMaxSize()
                .align(Alignment.TopCenter)
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
                .background(MaterialTheme.colors.surface)
        ) {

            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h4,
                modifier = Modifier.align(Alignment.Center)
            )

            Text(
                text = "跳过",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .background(
                        MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .align(Alignment.CenterEnd)
            )

        }

    }

}