package com.example.myfoodrecipeapp.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.myfoodrecipeapp.helper.ViewModelFactory
import com.example.myfoodrecipeapp.preferences.UserPreference
import com.example.myfoodrecipeapp.user.User
import com.example.myfoodrecipeapp.viewmodel.RegisterViewModel
import com.mansao.myfoodrecipeapp.databinding.ActivityRegisterBinding
import com.umk.catatanku.preferences.SettingPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel> {
        ViewModelFactory(
            UserPreference.getInstance(dataStore),
            application,
            SettingPreferences.getInstance(dataStore)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        registerSetup()
    }

    private fun registerSetup() {
        binding.apply {
            btnRegister.setOnClickListener {
                val username = edtUsername.text.toString()
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()

                when {
                    username.isEmpty() -> edtUsername.error = "required username"
                    email.isEmpty() -> edtEmail.error = "required email"
                    password.isEmpty() -> edtPassword.error = "required password"
                    else -> {
                        registerViewModel.saveUser(User(username, email, password, false))
                        AlertDialog.Builder(this@RegisterActivity).apply {
                            setTitle("Registrasi berhasil")
                            setMessage("lanjut?")
                            setPositiveButton("Ya") { _, _ ->
                                finish()
                            }
                            create()
                            show()
                        }
                    }
                }
            }
        }
    }
}