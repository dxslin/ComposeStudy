package com.slin.splayandroid.ui.home.ds

import com.slin.core.repository.IRemoteDataSource
import com.slin.splayandroid.data.api.ApiService
import com.slin.splayandroid.data.bean.BannerBean
import com.slin.splayandroid.data.bean.isSuccessful
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/**
 * author: slin
 * date: 2021/8/27
 * description:
 *
 */
@ViewModelScoped
class HomeDataSource @Inject constructor(private val apiService: ApiService) : IRemoteDataSource {


    suspend fun getBanner(): List<BannerBean> {
        return try {
            val response = apiService.getBanner()
            if (response.isSuccessful()) {
                response.data
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

}