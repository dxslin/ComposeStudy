package com.slin.splayandroid.ui.test

import androidx.paging.PagingData
import com.slin.core.repository.CoreRepositoryRemote
import com.slin.splayandroid.data.bean.ArticleBean
import com.slin.splayandroid.data.bean.BannerBean
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
class TestRepository @Inject constructor(remoteDataSource: TestDataSource) :
    CoreRepositoryRemote<TestDataSource>(remoteDataSource) {


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