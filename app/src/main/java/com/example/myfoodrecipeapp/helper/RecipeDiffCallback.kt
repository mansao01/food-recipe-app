package com.example.myfoodrecipeapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.myfoodrecipeapp.database.Recipe

class RecipeDiffCallback(
    private val mOldRecipeList: List<Recipe>,
    private val mNewRecipeList: List<Recipe>
) : DiffUtil.Callback() {
    override fun getOldListSize() = mOldRecipeList.size
    override fun getNewListSize() = mNewRecipeList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldRecipeList[oldItemPosition].id == mNewRecipeList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newEmployee = mNewRecipeList[newItemPosition]
        val oldEmployee = mOldRecipeList[oldItemPosition]

        return oldEmployee.title == newEmployee.title
    }
}