package com.example.picabee.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.picabee.Hit

@Dao
interface ImageDao {
    @Query("SELECT * FROM image_hit")
    fun getAll(): LiveData<List<Hit>>

    @Insert
    fun insert(vararg hit: Hit)

    @Insert
    fun insert(list: List<Hit>)
}