package com.slin.splayandroid.di

import android.content.Context
import androidx.room.Room
import com.slin.splayandroid.data.db.ChannelDao
import com.slin.splayandroid.data.db.SPlayAndroidDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * author: slin
 * date: 2021/8/26
 * description: 依赖注入项 数据库 Room
 *
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSPlayAndroidDatabase(@ApplicationContext context: Context): SPlayAndroidDatabase {
        return Room.databaseBuilder(context, SPlayAndroidDatabase::class.java, "splay_android.db")
            // This is not recommended for normal apps, but the goal of this sample isn't to
            // showcase all of Room.
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideChannelDao(db: SPlayAndroidDatabase): ChannelDao {
        return db.channelDao()
    }

}