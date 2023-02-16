package com.davega.remote.repository

import com.davega.data.models.RecipeEntity
import com.davega.data.repository.RecipeRemote
import com.davega.remote.mappers.RecipeEntityMapper
import com.davega.remote.api.RecipeService
import javax.inject.Inject

class RecipeRemoteImpl @Inject constructor(
    private val recipeService: RecipeService,
    private val recipeEntityMapper: RecipeEntityMapper
) : RecipeRemote {

    override suspend fun getRecipes(): List<RecipeEntity> {
        return recipeService.getRecipes().map { recipeModel ->
            recipeEntityMapper.mapFromModel(recipeModel)
        }
    }

    override suspend fun getRecipe(recipeId: Long): RecipeEntity {
        return recipeEntityMapper.mapFromModel(recipeService.getRecipe(recipeId))
    }
}