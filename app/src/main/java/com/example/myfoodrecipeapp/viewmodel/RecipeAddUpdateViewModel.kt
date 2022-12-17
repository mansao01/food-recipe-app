package com.example.myfoodrecipeapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.myfoodrecipeapp.database.Recipe
import com.example.myfoodrecipeapp.repository.RecipeRepository

class RecipeAddUpdateViewModel(application: Application) : ViewModel() {
    private val recipeRepository = RecipeRepository(application)

    fun insert(recipe: Recipe) {
        recipeRepository.insert(recipe)
    }

    fun update(recipe: Recipe) {
        recipeRepository.update(recipe)
    }

    fun delete(recipe: Recipe) {
        recipeRepository.delete(recipe)
    }
}