package com.davega.data

import com.davega.data.mapper.RecipeMapper
import com.davega.data.source.RecipeDataSourceFactory
import com.davega.domain.models.Recipe
import com.davega.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeRepositoryImp @Inject constructor(
    private val dataSourceFactory: RecipeDataSourceFactory,
    private val recipeMapper: RecipeMapper,
) : RecipeRepository {

    override suspend fun getRecipes(): Flow<List<Recipe>> = flow {
        val isCached = dataSourceFactory.getCacheDataSource().isCached()
        val recipeList =
            dataSourceFactory.getDataStore(isCached).getRecipes().map { recipeEntity ->
                recipeMapper.mapFromEntity(recipeEntity)
            }
        saveRecipes(recipeList)
        emit(recipeList)
    }

    override suspend fun getRecipe(recipeId: Long): Flow<Recipe> = flow {
        var recipe = dataSourceFactory.getCacheDataSource().getRecipe(recipeId)
        if (recipe.urlImage.isEmpty()) {
            recipe = dataSourceFactory.getRemoteDataSource().getRecipe(recipeId)
        }
        emit(
            recipeMapper.mapFromEntity(recipe)
        )
    }

    override suspend fun getRecipeByName(name: String): Flow<List<Recipe>> = flow {
        val recipeList = dataSourceFactory.getCacheDataSource().getRecipesByName(name).map { recipeEntity ->
            recipeMapper.mapFromEntity(recipeEntity)
        }
        emit(recipeList)
    }

    override suspend fun saveRecipes(listRecipes: List<Recipe>) {
        val recipeEntities = listRecipes.map { recipe ->
            recipeMapper.mapToEntity(recipe)
        }
        dataSourceFactory.getCacheDataSource().saveRecipes(recipeEntities)
    }


}