package com.example.myfoodrecipeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfoodrecipeapp.preferences.UserPreference
import com.example.myfoodrecipeapp.user.User
import kotlinx.coroutines.launch

class RegisterViewModel(private val pref: UserPreference) : ViewModel() {
    fun saveUser(user: User) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}