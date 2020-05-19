package com.example.picabee.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.picabee.entity.Hit

@Dao
interface ImageDao {
    @Query("SELECT * FROM image_hit")
    fun postsAll(): DataSource.Factory<Int, Hit>

    @Query("DELETE FROM image_hit")
    fun deleteAll(): Int

    @Query("SELECT count(*) FROM image_hit")
    fun count(): Int

    @Insert
    fun insert(vararg hit: Hit)

    @Insert
    fun insert(list: List<Hit>)
}