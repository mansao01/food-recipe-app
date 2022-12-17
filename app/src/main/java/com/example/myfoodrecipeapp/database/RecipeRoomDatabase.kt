package com.example.myfoodrecipeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class], version = 1)
abstract class RecipeRoomDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): RecipeRoomDatabase {
            if (INSTANCE == null) {
                synchronized(RecipeRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RecipeRoomDatabase::class.java, "recipe_database"
                    ).build()
                }
            }
            return INSTANCE as RecipeRoomDatabase
        }
    }
}