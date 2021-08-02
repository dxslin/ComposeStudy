package com.slin.splayandroid.di

import android.app.Application
import android.content.Context
import com.slin.core.config.CoreConfig
import com.slin.core.config.DefaultConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * author: slin
 * date: 2021/8/2
 * description:
 *
 */

@Module
@InstallIn(SingletonComponent::class)
object ConfigModule {

    @Provides
    fun provideAppConfig(
        @ApplicationContext context: Context,
    ): CoreConfig {
        return CoreConfig(
            application = context as Application,
            baseUrl = DefaultConfig.BASE_URL,
            httpLogLevel = DefaultConfig.HTTP_LOG_LEVEL,
        )
    }

}