package com.example.myfoodrecipeapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.myfoodrecipeapp.helper.ViewModelFactory
import com.example.myfoodrecipeapp.preferences.UserPreference
import com.example.myfoodrecipeapp.viewmodel.ProfileViewModel
import com.mansao.myfoodrecipeapp.R
import com.mansao.myfoodrecipeapp.databinding.ActivityProfileBinding
import com.umk.catatanku.preferences.SettingPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory(
            UserPreference.getInstance(dataStore),
            application,
            SettingPreferences.getInstance(dataStore)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.profile)
        }

        setData()
    }

    private fun setData() {
        profileViewModel.getUser().observe(this) {
            if (it.isLogin) {
                binding.apply {
                    tvEmail.text = it.email
                    tvUsername.text = it.username
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}