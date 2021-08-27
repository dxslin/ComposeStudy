package com.slin.splayandroid.ui.home.ds

import com.slin.core.repository.CoreRepositoryRemote
import com.slin.splayandroid.data.bean.BannerBean
import dagger.hilt.android.scopes.ViewModelScoped
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

}