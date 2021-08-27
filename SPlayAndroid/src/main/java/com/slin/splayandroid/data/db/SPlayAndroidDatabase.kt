package com.slin.splayandroid.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.slin.splayandroid.data.bean.ChildrenBean

/**
 * author: slin
 * date: 2021/8/26
 * description:
 *
 */
@Database(entities = [ChildrenBean::class], version = 1, exportSchema = false)
abstract class SPlayAndroidDatabase : RoomDatabase() {

    abstract fun channelDao(): ChannelDao

}