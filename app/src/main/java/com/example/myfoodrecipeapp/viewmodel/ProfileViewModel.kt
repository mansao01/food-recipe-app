package com.example.myfoodrecipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.myfoodrecipeapp.user.User
import com.example.myfoodrecipeapp.preferences.UserPreference

class ProfileViewModel(private val pref: UserPreference): ViewModel() {
    fun getUser(): LiveData<User>{
        return pref.getUser().asLiveData()
    }
}