package com.example.myfoodrecipeapp.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfoodrecipeapp.preferences.UserPreference
import com.example.myfoodrecipeapp.viewmodel.*
import com.umk.catatanku.preferences.SettingPreferences

class ViewModelFactory(
    private val userPreference: UserPreference,
    private val application: Application,
    private val settingPreferences: SettingPreferences
) : ViewModelProvider.NewInstanceFactory() {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(userPreference, application) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(userPreference) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userPreference) as T
            }
            modelClass.isAssignableFrom(RecipeAddUpdateViewModel::class.java) -> {
                RecipeAddUpdateViewModel(application) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userPreference) as T
            }
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(settingPreferences) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            userPreference: UserPreference,
            application: Application,
            settingPreferences: SettingPreferences
        ): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(userPreference, application, settingPreferences)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}