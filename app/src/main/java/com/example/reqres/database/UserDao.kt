package com.example.reqres.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserList(list: List<UserTable>)

    @Query("SELECT * FROM UserTable")
    fun getUserList(): List<UserTable>
//
//
//    @Query("SELECT * FROM MovieTable WHERE name LIKE :search ")
//    fun findMovieWithName(search: String): List<MovieTable>
}