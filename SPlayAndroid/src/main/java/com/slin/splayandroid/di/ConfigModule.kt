package com.slin.splayandroid.di

import android.app.Application
import android.content.Context
import com.slin.core.config.CoreConfig
import com.slin.splayandroid.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

/**
 * author: slin
 * date: 2021/8/2
 * description: Score配置
 *
 */

object SPlayAndroidConfig {

    const val BASE_URL: String = "https://www.wanandroid.com/"
    val HTTP_LOG_LEVEL: HttpLoggingInterceptor.Level = when (BuildConfig.DEBUG) {
        true -> HttpLoggingInterceptor.Level.BODY
        false -> HttpLoggingInterceptor.Level.NONE
    }

}

@Module
@InstallIn(SingletonComponent::class)
object ConfigModule {

    @Provides
    fun provideAppConfig(
        @ApplicationContext context: Context,
        @SaveCookieInterceptor saveCookieInterceptor: Interceptor,
        @AddCookieInterceptor addCookieInterceptor: Interceptor,
    ): CoreConfig {
        return CoreConfig(
            application = context as Application,
            baseUrl = SPlayAndroidConfig.BASE_URL,
            httpLogLevel = SPlayAndroidConfig.HTTP_LOG_LEVEL,
            customInterceptors = listOf(saveCookieInterceptor, addCookieInterceptor)
        )
    }

}