package com.example.myfoodrecipeapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfoodrecipeapp.adapter.RecipeListAdapter
import com.example.myfoodrecipeapp.helper.ViewModelFactory
import com.example.myfoodrecipeapp.preferences.UserPreference
import com.example.myfoodrecipeapp.viewmodel.MainViewModel
import com.example.myfoodrecipeapp.viewmodel.SettingViewModel
import com.mansao.myfoodrecipeapp.R
import com.mansao.myfoodrecipeapp.databinding.ActivityMainBinding
import com.umk.catatanku.preferences.SettingPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecipeListAdapter

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory(
            UserPreference.getInstance(dataStore),
            this.application,
            SettingPreferences.getInstance(dataStore)
        )
    }

    private val settingsViewModel by viewModels<SettingViewModel> {
        ViewModelFactory(
            UserPreference.getInstance(dataStore),
            this.application,
            SettingPreferences.getInstance(dataStore)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RecipeListAdapter()


        mainViewModel.getUser().observe(this) {
            if (!it.isLogin) {
                val intentToLogin = Intent(this, LoginActivity::class.java)
                startActivity(intentToLogin)
            }
        }

        setRecyclerview()
        binding.fabAdd.setOnClickListener {
            val intentToAddUpdateData = Intent(this, RecipeAddUpdateActivity::class.java)
            startActivity(intentToAddUpdateData)
        }

        settingsViewModel.getThemeSettings().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setRecyclerview() {
        binding.apply {
            rvRecipe.adapter = adapter
            rvRecipe.layoutManager = LinearLayoutManager(this@MainActivity)
            rvRecipe.setHasFixedSize(true)
        }
        mainViewModel.getAllRecipe().observe(this) {
            if (it.isEmpty()) {
                binding.tvEmptyRv.visibility = View.VISIBLE
                binding.rvRecipe.visibility = View.GONE
            } else {
                adapter.setListRecipe(it)
                binding.rvRecipe.visibility = View.VISIBLE
                binding.tvEmptyRv.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_profile -> {
                val intentToProfile = Intent(this, ProfileActivity::class.java)
                startActivity(intentToProfile)
            }
            R.id.action_setting -> {
                val intentToSetting = Intent(this, SettingActivity::class.java)
                startActivity(intentToSetting)
            }
            R.id.action_logout -> mainViewModel.logOut()
        }
        return super.onOptionsItemSelected(item)
    }

}