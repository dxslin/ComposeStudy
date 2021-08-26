package com.slin.splayandroid.data.db

import androidx.room.Database
import com.slin.splayandroid.data.bean.ChildrenBean

/**
 * author: slin
 * date: 2021/8/26
 * description:
 *
 */
@Database(entities = [ChildrenBean::class], version = 1, exportSchema = false)
abstract class SPlayAndroidDatabase {

    abstract fun channelDao(): ChannelDao

}