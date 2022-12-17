package com.example.myfoodrecipeapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(recipe: Recipe)

    @Delete
    fun delete(recipe: Recipe)

    @Update
    fun update(recipe: Recipe)

    @Query("SELECT * FROM recipe ORDER BY id ASC")
    fun getAllData(): LiveData<List<Recipe>>
}