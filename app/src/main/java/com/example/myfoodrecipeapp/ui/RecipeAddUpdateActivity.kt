package com.example.myfoodrecipeapp.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.myfoodrecipeapp.database.Recipe
import com.example.myfoodrecipeapp.helper.ViewModelFactory
import com.example.myfoodrecipeapp.preferences.UserPreference
import com.example.myfoodrecipeapp.viewmodel.RecipeAddUpdateViewModel
import com.mansao.myfoodrecipeapp.R
import com.mansao.myfoodrecipeapp.databinding.ActivityRecipeAddUpdateBinding
import com.umk.catatanku.preferences.SettingPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RecipeAddUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeAddUpdateBinding
    private var isEdit = false
    private var recipe: Recipe? = null
    private val recipeAddUpdateViewModel by viewModels<RecipeAddUpdateViewModel> {
        ViewModelFactory(
            UserPreference.getInstance(dataStore),
            application,
            SettingPreferences.getInstance(dataStore)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_DATA, Recipe::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_DATA)
        }
        if (recipe != null) {
            isEdit = true
        } else {
            recipe = Recipe()
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (recipe != null) {
                recipe?.let {
                    binding.apply {
                        edtTitle.setText(it.title)
                        edtServing.setText(it.serving)
                        edtDifficulty.setText(it.difficulty)
                        edtDescription.setText(it.description)
                        edtIngredient.setText(it.ingredients)
                        edtStep.setText(it.step)
                    }
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSubmit.text = btnTitle

        binding.btnSubmit.setOnClickListener {
            saveUpdateData()
        }

    }

    private fun saveUpdateData() {
        binding.apply {
            val title = edtTitle.text.toString()
            val serving = edtServing.text.toString()
            val difficulty = edtDifficulty.text.toString()
            val description = edtDescription.text.toString()
            val ingredient = edtIngredient.text.toString()
            val step = edtStep.text.toString()

            when {
                title.isEmpty() -> edtTitle.error = getString(R.string.empty)
                serving.isEmpty() -> edtServing.error = getString(R.string.empty)
                difficulty.isEmpty() -> edtDifficulty.error = getString(R.string.empty)
                description.isEmpty() -> edtDescription.error = getString(R.string.empty)
                ingredient.isEmpty() -> edtIngredient.error = getString(R.string.empty)
                step.isEmpty() -> edtStep.error = getString(R.string.empty)
                else -> {
                    recipe.let {
                        it?.title = title
                        it?.serving = serving
                        it?.difficulty = difficulty
                        it?.description = description
                        it?.ingredients = ingredient
                        it?.step = step
                        if (isEdit) {
                            recipeAddUpdateViewModel.update(recipe as Recipe)
                            showToast(getString(R.string.update))
                        } else {
                            recipeAddUpdateViewModel.insert(recipe as Recipe)
                            showToast(getString(R.string.added))
                        }
                        finish()
                    }
                }
            }
        }
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.deleted)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    recipeAddUpdateViewModel.delete(recipe as Recipe)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isEdit) menuInflater.inflate(R.menu.menu_update, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(message: String) {
        Toast.makeText(this@RecipeAddUpdateActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateUpTo(upIntent: Intent?): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.navigateUpTo(upIntent)
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
}