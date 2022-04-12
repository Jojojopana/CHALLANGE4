package com.binar.challange_part4.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.binar.challange_part4.Student
import com.binar.challange_part4.User

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    fun insert(user: User): Long

    @Query("SELECT * FROM User WHERE username = :userName AND password = :password")
    fun login(userName: String, password: String): User?
}