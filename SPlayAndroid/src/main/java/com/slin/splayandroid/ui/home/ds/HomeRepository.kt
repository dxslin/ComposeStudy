package com.slin.splayandroid.ui.home.ds

import androidx.paging.PagingData
import com.slin.core.repository.CoreRepositoryRemote
import com.slin.splayandroid.data.bean.ArticleBean
import com.slin.splayandroid.data.bean.BannerBean
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
class HomeRepository @Inject constructor(remoteDataSource: HomeDataSource) :
    CoreRepositoryRemote<HomeDataSource>(remoteDataSource) {


    suspend fun getBanner(): List<BannerBean> {
        return remoteDataSource.getBanner()
    }

    fun getHomeArticles(cid: Int? = null): Flow<PagingData<ArticleBean>> {
        return remoteDataSource.getHomeArticles(cid)
    }

    fun getDailyQuestions(): Flow<PagingData<ArticleBean>> {
        return remoteDataSource.getDailyQuestions()
    }

    fun getPiazzaList(): Flow<PagingData<ArticleBean>> {
        return remoteDataSource.getPiazzaList()
    }

}