package com.davega.data.repository

import com.davega.data.models.RecipeEntity

interface RecipeDataSource {
    // Remote and cache
    suspend fun getRecipes(): List<RecipeEntity>
    suspend fun getRecipe(recipeId: Long): RecipeEntity

    // Cache
    suspend fun saveRecipes(listRecipes: List<RecipeEntity>)
    suspend fun isCached(): Boolean
}