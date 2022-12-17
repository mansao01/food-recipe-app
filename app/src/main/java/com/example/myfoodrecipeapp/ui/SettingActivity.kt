package com.example.myfoodrecipeapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.myfoodrecipeapp.helper.ViewModelFactory
import com.example.myfoodrecipeapp.preferences.UserPreference
import com.example.myfoodrecipeapp.viewmodel.SettingViewModel
import com.mansao.myfoodrecipeapp.R
import com.mansao.myfoodrecipeapp.databinding.ActivitySettingBinding
import com.umk.catatanku.preferences.SettingPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val settingsViewModel by viewModels<SettingViewModel> {
        ViewModelFactory(
            UserPreference.getInstance(dataStore),
            application,
            SettingPreferences.getInstance(dataStore)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.setting)
            this.setDisplayHomeAsUpEnabled(true)
        }

        val switchTheme = binding.switchTheme
        settingsViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }
        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingsViewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}