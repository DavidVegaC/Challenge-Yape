package com.davega.cache

import com.davega.cache.dao.RecipeDao
import com.davega.cache.mapper.RecipeCacheMapper
import com.davega.cache.utils.CachePreferencesHelper
import com.davega.data.models.RecipeEntity
import com.davega.data.repository.RecipeCache
import javax.inject.Inject

class RecipeCacheImpl @Inject constructor(
    private val recipeDao: RecipeDao,
    private val recipeCacheMapper: RecipeCacheMapper,
    private val preferencesHelper: CachePreferencesHelper
) : RecipeCache {

    override suspend fun getRecipes(): List<RecipeEntity> {
        return recipeDao.getRecipes().map { cacheRecipe ->
            recipeCacheMapper.mapFromCached(cacheRecipe)
        }
    }

    override suspend fun getRecipe(recipeId: Long): RecipeEntity {
        return recipeCacheMapper.mapFromCached(recipeDao.getRecipe(recipeId))
    }

    override suspend fun saveRecipes(listRecipes: List<RecipeEntity>) {
        return recipeDao.addRecipe(
            *listRecipes.map {
                recipeCacheMapper.mapToCached(it)
            }.toTypedArray()
        )
    }

    override suspend fun isCached(): Boolean {
        return recipeDao.getRecipes().isNotEmpty()
    }

    override suspend fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    override suspend fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }

    companion object {
        /**
         * Expiration time set to 5 minutes
         */
        const val EXPIRATION_TIME = (60 * 5 * 1000).toLong()
    }
}