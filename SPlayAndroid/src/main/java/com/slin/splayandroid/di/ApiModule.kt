package com.slin.splayandroid.di

import com.slin.splayandroid.data.api.HomeService
import com.slin.splayandroid.data.api.PrivateService
import com.slin.splayandroid.data.api.ProjectService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * author: slin
 * date: 2021/8/26
 * description:
 *
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideHomeService(retrofit: Retrofit): HomeService {
        return retrofit.create(HomeService::class.java)
    }

    @Provides
    @Singleton
    fun providePrivateService(retrofit: Retrofit): PrivateService {
        return retrofit.create(PrivateService::class.java)
    }

    @Provides
    @Singleton
    fun provideProjectService(retrofit: Retrofit): ProjectService {
        return retrofit.create(ProjectService::class.java)
    }


}