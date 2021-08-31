package com.slin.splayandroid.data.api

import com.slin.splayandroid.data.bean.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * author: slin
 * date: 2021/8/30
 * description:
 *
 */
interface HomeService {

    /**
     * 获取文章列表
     * @param curPage 页码从0开始
     * @return
     */
    @GET("article/list/{curPage}/json")
    suspend fun getArticlesList(
        @Path("curPage") curPage: Int,
        @Query("cid") id: Int? = null
    ): ApiResponse<Page<List<ArticleBean>>>

    /**
     * 首页Banner
     * @ return
     */
    @GET("banner/json")
    suspend fun getBanner(): ApiResponse<List<BannerBean>>

    /**
     * 问答
     */
    @GET("wenda/list/{page}/json")
    suspend fun getDailyQuestion(@Path("page") page: Int): ApiResponse<Page<List<ArticleBean>>>

    /**
     * 广场
     */
    @GET("user_article/list/{page}/json")
    suspend fun getPiazzaList(@Path("page") page: Int): ApiResponse<Page<List<ArticleBean>>>

    /**
     * 知识体系
     * @return
     */
    @GET("tree/json")
    suspend fun getSystem(): ApiResponse<List<TreeBean>>


}