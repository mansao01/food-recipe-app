package com.example.myfoodrecipeapp.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "serving")
    var serving: String = "",

    @ColumnInfo(name = "difficulty")
    var difficulty: String = "",

    @ColumnInfo(name = "description")
    var description: String = "",

    @ColumnInfo(name = "ingredients")
    var ingredients: String = "",

    @ColumnInfo(name = "step")
    var step: String = ""

) : Parcelable