package com.slin.splayandroid.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slin.core.utils.fromJson
import com.slin.splayandroid.R
import com.slin.splayandroid.data.bean.ArticleBean

/**
 * author: slin
 * date: 2021/9/1
 * description: 文章列表项
 *
 */

@Composable
fun ArticleItem(articleBean: ArticleBean, onItemClick: () -> Unit = {}) {
    Column(modifier = Modifier
        .background(MaterialTheme.colors.surface)
        .clickable { onItemClick() }
        .padding(top = 16.dp, start = 16.dp, end = 16.dp)) {

        Text(text = articleBean.title, style = MaterialTheme.typography.subtitle1)

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.chapter_) + articleBean.chapterName,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .weight(1f)
                )
                Text(
                    text = articleBean.niceDate,
                    modifier = Modifier,
                    style = MaterialTheme.typography.caption,
                )
            }
            Text(
                text = stringResource(id = R.string.author_) + articleBean.author,
                modifier = Modifier,
                style = MaterialTheme.typography.caption
            )

        }
        ListDivider(Modifier.padding(top = 16.dp))
    }
}

@Composable
fun ListDivider(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier,
        color = MaterialTheme.colors.onSurface.copy(0.1f),
        thickness = 1.dp,
        startIndent = 0.dp
    )
}

@Preview
@Composable
fun PreviewArticleItem() {
    val article =
        "{\"apkLink\":\"\",\"audit\":1,\"author\":\"酱爆大头菜\",\"canEdit\":false,\"chapterId\":502,\"chapterName\":\"自助\",\"collect\":false,\"courseId\":13,\"desc\":\"\",\"descMd\":\"\",\"envelopePic\":\"\",\"fresh\":false,\"host\":\"\",\"id\":19565,\"link\":\"https://juejin.cn/post/7000615997413523486\",\"niceDate\":\"2021-08-26 14:11\",\"niceShareDate\":\"2021-08-26 14:11\",\"origin\":\"\",\"prefix\":\"\",\"projectLink\":\"\",\"publishTime\":1629958260000,\"realSuperChapterId\":493,\"selfVisible\":0,\"shareDate\":1629958260000,\"shareUser\":\"吊儿郎当\",\"superChapterId\":494,\"superChapterName\":\"广场Tab\",\"tags\":[],\"title\":\"入职阿里4个月了，真的每天都在拧螺丝？\",\"type\":0,\"userId\":2554,\"visible\":1,\"zan\":0}"
    val articleBean: ArticleBean = article.fromJson()
    ArticleItem(articleBean = articleBean)
}


