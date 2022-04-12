package com.binar.challange_part4.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.binar.challange_part4.Student

@Dao
interface StudentDao {

    @Query("SELECT * FROM Student")
    fun getAllStudent(): List<Student>

    @Insert(onConflict = REPLACE)
    fun insertStudent(student: Student): Long

    @Update
    fun updateStudent(student: Student): Int

    @Delete
    fun deleteStudent(student: Student): Int

}