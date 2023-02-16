package com.davega.data.repository

import com.davega.data.models.RecipeEntity

interface RecipeRemote {
    suspend fun getRecipes(): List<RecipeEntity>
    suspend fun getRecipe(recipeId: Long): RecipeEntity
}