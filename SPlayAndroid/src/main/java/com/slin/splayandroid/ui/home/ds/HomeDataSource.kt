package com.slin.splayandroid.ui.home.ds

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.slin.core.repository.IRemoteDataSource
import com.slin.splayandroid.constants.PagingConst
import com.slin.splayandroid.data.api.HomeService
import com.slin.splayandroid.data.args.Arg1
import com.slin.splayandroid.data.bean.ArticleBean
import com.slin.splayandroid.data.bean.BannerBean
import com.slin.splayandroid.data.bean.isSuccessful
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * author: slin
 * date: 2021/8/27
 * description:
 *
 */
@ViewModelScoped
class HomeDataSource @Inject constructor(private val homeService: HomeService) : IRemoteDataSource {

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

    fun getHomeArticles(cid: Int? = null): Flow<PagingData<ArticleBean>> {
        return Pager(
            config = PagingConst.pagingConfig, Arg1(arg1 = cid)
        ) {
            HomeArticlesPagingSource(homeService)
        }.flow
    }

}


class HomeArticlesPagingSource(private val homeService: HomeService) :
    PagingSource<Arg1<Int>, ArticleBean>() {

    override fun getRefreshKey(state: PagingState<Arg1<Int>, ArticleBean>): Arg1<Int>? {
        return state.pages.last().nextKey?.reset()
    }

    override suspend fun load(params: LoadParams<Arg1<Int>>): LoadResult<Arg1<Int>, ArticleBean> {
        return PagingConst.load(params.key) {
            homeService.getArticlesList(it.page, it.arg1)
        }
    }
}

