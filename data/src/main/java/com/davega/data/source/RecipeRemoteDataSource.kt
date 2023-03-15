package com.davega.data.source

import com.davega.data.models.RecipeEntity
import com.davega.data.repository.RecipeCache
import com.davega.data.repository.RecipeDataSource
import com.davega.data.repository.RecipeRemote
import javax.inject.Inject

class RecipeRemoteDataSource @Inject constructor(
    private val recipeRemote: RecipeRemote
) : RecipeDataSource {
    override suspend fun getRecipes(): List<RecipeEntity> {
        return recipeRemote.getRecipes()
    }

    override suspend fun getRecipe(recipeId: Long): RecipeEntity {
        return recipeRemote.getRecipe(recipeId)
    }

    override suspend fun saveRecipes(listRecipes: List<RecipeEntity>) {
        throw UnsupportedOperationException("Save Recipe is not supported for RemoteDataSource.")
    }

    override suspend fun isCached(): Boolean {
        throw UnsupportedOperationException("Cached is not supported for RemoteDataSource.")
    }
}