package com.example.myfoodrecipeapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myfoodrecipeapp.database.Recipe
import com.example.myfoodrecipeapp.database.RecipeDao
import com.example.myfoodrecipeapp.database.RecipeRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RecipeRepository(application: Application) {
    private val recipeDao: RecipeDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = RecipeRoomDatabase.getDatabase(application)
        recipeDao = db.recipeDao()
    }

    fun getAllData(): LiveData<List<Recipe>> = recipeDao.getAllData()

    fun insert(recipe: Recipe) {
        executorService.execute {
            recipeDao.insert(recipe)
        }
    }

    fun delete(recipe: Recipe) {
        executorService.execute {
            recipeDao.delete(recipe)
        }
    }

    fun update(recipe: Recipe) {
        executorService.execute {
            recipeDao.update(recipe)
        }
    }


}