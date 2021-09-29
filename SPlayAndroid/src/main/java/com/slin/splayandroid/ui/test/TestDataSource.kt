package com.slin.splayandroid.ui.test

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.slin.core.repository.IRemoteDataSource
import com.slin.splayandroid.data.api.HomeService
import com.slin.splayandroid.data.args.Arg0
import com.slin.splayandroid.data.args.Arg1
import com.slin.splayandroid.data.bean.ArticleBean
import com.slin.splayandroid.data.bean.BannerBean
import com.slin.splayandroid.data.bean.isSuccessful
import com.slin.splayandroid.utils.PagingHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * author: slin
 * date: 2021/8/27
 * description:
 *
 */
@Singleton
class TestDataSource @Inject constructor(private val homeService: HomeService) : IRemoteDataSource {

    /**
     * Banner 数据，失败返回emptyList
     */
    suspend fun getBanner(): List<BannerBean> {
        return try {
            val response = homeService.getBanner()
            if (response.isSuccessful()) {
                response.data!!
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * 首页文章列表
     */
    fun getHomeArticles(cid: Int? = null): Flow<PagingData<ArticleBean>> {
        return Pager(
            config = PagingHelper.pagingConfig, Arg1(arg1 = cid)
        ) {
            HomeArticlesPagingSource(homeService)
        }.flow
    }

    /**
     * 每日一问文章列表
     */
    fun getDailyQuestions(): Flow<PagingData<ArticleBean>> {
        return Pager(config = PagingHelper.pagingConfig, Arg0()) {
            DailyQuestionPagingSource(homeService)
        }.flow
    }

    /**
     * 广场文章列表
     */
    fun getPiazzaList(): Flow<PagingData<ArticleBean>> {
        return Pager(config = PagingHelper.pagingConfig, Arg0()) {
            PiazzaPagingSource(homeService)
        }.flow
    }

}


/**
 * 首页列表数据
 */
class HomeArticlesPagingSource(private val homeService: HomeService) :
    PagingSource<Arg1<Int>, ArticleBean>() {

    override fun getRefreshKey(state: PagingState<Arg1<Int>, ArticleBean>): Arg1<Int>? {
        return null
    }

    override suspend fun load(params: LoadParams<Arg1<Int>>): LoadResult<Arg1<Int>, ArticleBean> {
        return PagingHelper.load(params.key) {
            homeService.getArticlesList(it.page, it.arg1)
        }
    }
}

/**
 * 每日一问列表数据
 */
class DailyQuestionPagingSource(private val homeService: HomeService) :
    PagingSource<Arg0, ArticleBean>() {
    override fun getRefreshKey(state: PagingState<Arg0, ArticleBean>): Arg0? {
        return null
    }

    override suspend fun load(params: LoadParams<Arg0>): LoadResult<Arg0, ArticleBean> {
        return PagingHelper.load(params.key) {
            homeService.getDailyQuestion(it.page)
        }
    }
}

/**
 * 广场数据
 */
class PiazzaPagingSource(private val homeService: HomeService) : PagingSource<Arg0, ArticleBean>() {
    override fun getRefreshKey(state: PagingState<Arg0, ArticleBean>): Arg0? {
        return null
    }

    override suspend fun load(params: LoadParams<Arg0>): LoadResult<Arg0, ArticleBean> {
        return PagingHelper.load(params.key) {
            homeService.getPiazzaList(it.page)
        }
    }
}
