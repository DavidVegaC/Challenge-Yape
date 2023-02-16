package com.davega.domain.repository

import com.davega.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    // Remote and cache
    suspend fun getRecipes(): Flow<List<Recipe>>
    suspend fun getRecipe(recipeId: Long): Flow<Recipe>
    suspend fun getRecipeByName(name: String): Flow<List<Recipe>>

    // Cache
    suspend fun saveRecipes(listRecipes: List<Recipe>)
}