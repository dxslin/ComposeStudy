package com.slin.splayandroid.data.api

import com.slin.splayandroid.data.bean.AccountInfo
import com.slin.splayandroid.data.bean.ApiResponse
import com.slin.splayandroid.data.bean.CollectBean
import com.slin.splayandroid.data.bean.Page
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * author: slin
 * date: 2021/8/31
 * description:
 *
 */
interface PrivateService {

    /**
     * 登陆
     */
    @POST("user/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): ApiResponse<AccountInfo>

    /**
     * 登出
     */
    @GET("user/logout/json")
    suspend fun logout(): ApiResponse<Any>

    /**
     * 获取收藏列表
     * @param curPage 从第0页开始
     * @return
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectList(@Path("page") page: Int): ApiResponse<Page<List<CollectBean>>>

    /**
     * 收藏
     */
    @POST("lg/collect/{id}/json")
    suspend fun toCollect(@Path("id") id: Int): ApiResponse<Any>

    /**
     * 取消收藏
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun cancelCollect(@Path("id") id: Int): ApiResponse<Any>
}