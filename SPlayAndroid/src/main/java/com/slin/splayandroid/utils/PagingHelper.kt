package com.slin.splayandroid.utils

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.slin.splayandroid.data.args.Arg
import com.slin.splayandroid.data.bean.ApiResponse
import com.slin.splayandroid.data.bean.Page
import com.slin.splayandroid.data.bean.getFailException
import com.slin.splayandroid.data.bean.isSuccessful

/**
 * author: slin
 * date: 2021/8/31
 * description:
 *
 */

object PagingHelper {

    /**
     * 起始页码，有些可能是从 1 开始的
     */
    const val START_PAGE_NUM = 0

    /**
     * 每页数量
     */
    private const val PER_PAGE_NUM = 20

    val pagingConfig = PagingConfig(
        pageSize = PER_PAGE_NUM,
        enablePlaceholders = false,
        initialLoadSize = PER_PAGE_NUM,
        prefetchDistance = 2,
    )

    /**
     * 公用代码，直接将对应的网络请求转化为 Paging load 结果
     *
     * ```
     * return PagingHelper.load(params.key) {
     *     homeService.getArticlesList(it.page, it.arg1)
     * }
     * ```
     */
    suspend fun <Key : Arg<Key>, Value : Any> load(
        key: Key?,
        request: suspend (Key) -> ApiResponse<Page<List<Value>>>
    ): PagingSource.LoadResult<Key, Value> {
        return if (key == null) {
            PagingSource.LoadResult.Error(IllegalArgumentException("Key cannot be null"))
        } else {
            try {
                val response = request(key)
                if (response.isSuccessful()) {
                    val data = response.data
                    PagingSource.LoadResult.Page(
                        data = data?.datas ?: listOf(),
                        nextKey = if (key.page < data?.pageCount ?: 0) key.next() else null,
                        prevKey = key.previous(),
                    )
                } else {
                    PagingSource.LoadResult.Error(response.getFailException())
                }
            } catch (e: Exception) {
                PagingSource.LoadResult.Error(e)
            }
        }
    }


}

