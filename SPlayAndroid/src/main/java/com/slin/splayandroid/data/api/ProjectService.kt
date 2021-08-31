package com.slin.splayandroid.data.api

import com.slin.splayandroid.data.bean.ApiResponse
import com.slin.splayandroid.data.bean.Page
import com.slin.splayandroid.data.bean.ProjectContent
import com.slin.splayandroid.data.bean.ProjectTree
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * author: slin
 * date: 2021/8/30
 * description:
 *
 */
interface ProjectService {

    @GET("project/tree/json")
    suspend fun loadProjectTree(): ApiResponse<List<ProjectTree>>

    @GET("project/list/{path}/json")
    suspend fun loadProjectList(
        @Path("path") path: Int,
        @Query("cid") cid: Int
    ): ApiResponse<Page<List<ProjectContent>>>

}