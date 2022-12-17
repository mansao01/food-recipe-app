package com.example.myfoodrecipeapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myfoodrecipeapp.database.Recipe
import com.example.myfoodrecipeapp.helper.RecipeDiffCallback
import com.example.myfoodrecipeapp.ui.RecipeAddUpdateActivity
import com.mansao.myfoodrecipeapp.databinding.RecipeListItemBinding

class RecipeListAdapter : RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>() {
    private val listRecipe = ArrayList<Recipe>()

    fun setListRecipe(listRecipe: List<Recipe>) {
        val diffCallback = RecipeDiffCallback(this.listRecipe, listRecipe)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listRecipe.clear()
        this.listRecipe.addAll(listRecipe)
        diffResult.dispatchUpdatesTo(this)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val binding =
            RecipeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        holder.bind(listRecipe[position])
    }

    override fun getItemCount() = listRecipe.size

    inner class RecipeListViewHolder(private val binding: RecipeListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.apply {
                tvTitle.text = recipe.title
                tvDifficulty.text = recipe.difficulty
                tvServing.text = recipe.serving
                cardView.setOnClickListener {
                    val intent = Intent(itemView.context, RecipeAddUpdateActivity::class.java)
                    intent.putExtra(RecipeAddUpdateActivity.EXTRA_DATA, recipe)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}