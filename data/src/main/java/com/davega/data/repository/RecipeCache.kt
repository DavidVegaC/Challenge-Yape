package com.davega.data.repository

import com.davega.data.models.RecipeEntity

interface RecipeCache {
    suspend fun getRecipes(): List<RecipeEntity>
    suspend fun getRecipe(recipeId: Long): RecipeEntity
    suspend fun getRecipesByName(name: String): List<RecipeEntity>
    suspend fun saveRecipes(listRecipes: List<RecipeEntity>)
    suspend fun isCached(): Boolean
    suspend fun setLastCacheTime(lastCache: Long)
    suspend fun isExpired(): Boolean
}