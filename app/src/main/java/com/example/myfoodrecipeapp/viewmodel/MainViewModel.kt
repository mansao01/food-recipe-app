package com.example.myfoodrecipeapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myfoodrecipeapp.user.User
import com.example.myfoodrecipeapp.database.Recipe
import com.example.myfoodrecipeapp.preferences.UserPreference
import com.example.myfoodrecipeapp.repository.RecipeRepository
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreference, private val application: Application) :
    ViewModel() {
    private val mRecipeRepository: RecipeRepository = RecipeRepository(application)
    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun logOut() {
        viewModelScope.launch {
            pref.logout()
        }
    }


    fun getAllRecipe(): LiveData<List<Recipe>> {
        return mRecipeRepository.getAllData()
    }

}