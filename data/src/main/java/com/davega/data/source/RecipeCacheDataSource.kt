package com.davega.data.source

import com.davega.data.models.RecipeEntity
import com.davega.data.repository.RecipeCache
import com.davega.data.repository.RecipeDataSource
import javax.inject.Inject

class RecipeCacheDataSource @Inject constructor(
    private val recipeCache: RecipeCache
) : RecipeDataSource {
    override suspend fun getRecipes(): List<RecipeEntity> {
        return recipeCache.getRecipes()
    }

    override suspend fun getRecipe(recipeId: Long): RecipeEntity {
        return recipeCache.getRecipe(recipeId)
    }

    override suspend fun saveRecipes(listRecipes: List<RecipeEntity>) {
        return recipeCache.saveRecipes(listRecipes)
    }

    override suspend fun isCached(): Boolean {
        return recipeCache.isCached()
    }


}