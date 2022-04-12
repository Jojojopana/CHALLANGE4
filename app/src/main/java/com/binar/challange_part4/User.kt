package com.binar.challange_part4

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true) var id: Int?,

    @ColumnInfo(name = "username")
    var userName: String,

    @ColumnInfo(name = "password")
    var passwrd: String
) :Parcelable
