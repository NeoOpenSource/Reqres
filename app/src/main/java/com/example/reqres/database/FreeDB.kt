package com.example.reqres.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [UserTable::class], version = 1)
abstract class FreeDB : RoomDatabase() {
    abstract fun userDao():UserDao
    companion object {
        private var INSTANCE: FreeDB? = null
        fun getInstance(context: Context): FreeDB? {
            if (INSTANCE == null) {
                synchronized(FreeDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        FreeDB::class.java, "Free"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}