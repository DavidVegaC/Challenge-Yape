package com.davega.data.repository

import com.davega.data.models.RecipeEntity

interface RecipeDataSource {
    // Remote and cache
    suspend fun getRecipes(): List<RecipeEntity>
    suspend fun getRecipe(recipeId: Long): RecipeEntity
    suspend fun getRecipesByName(name: String): List<RecipeEntity>

    // Cache
    suspend fun saveRecipes(listRecipes: List<RecipeEntity>)
    suspend fun isCached(): Boolean
}