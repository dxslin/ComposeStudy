package com.slin.splayandroid.data.db

import androidx.room.*
import com.slin.splayandroid.data.bean.ChildrenBean

/**
 * author: slin
 * date: 2021/8/26
 * description:
 *
 */
@Dao
interface ChannelDao {

    @Query("SELECT * FROM channels")
    suspend fun getChannels(): List<ChildrenBean>

    @Query("SELECT * FROM channels WHERE id = :id")
    suspend fun getChannel(id: Int): ChildrenBean

    @Delete
    suspend fun delete(entity: ChildrenBean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ChildrenBean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: Collection<ChildrenBean>)

}