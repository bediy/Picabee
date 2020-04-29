package com.example.picabee.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.picabee.Hit

@Database(entities = [Hit::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?: build(context)
            }
        }

        private fun build(context: Context) = Room
                .databaseBuilder(context, AppDatabase::class.java, "database-app")
                .build()
    }
}