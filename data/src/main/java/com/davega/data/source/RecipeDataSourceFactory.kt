package com.davega.data.source

import com.davega.data.repository.RecipeCache
import com.davega.data.repository.RecipeDataSource
import javax.inject.Inject

open class RecipeDataSourceFactory @Inject constructor(
    private val recipeCache: RecipeCache,
    private val cacheDataSource: RecipeCacheDataSource,
    private val remoteDataSource: RecipeRemoteDataSource
) {

    open suspend fun getDataStore(isCached: Boolean): RecipeDataSource {
        return if (isCached && !recipeCache.isExpired()) {
            return getCacheDataSource()
        } else {
            getRemoteDataSource()
        }
    }

    fun getRemoteDataSource(): RecipeDataSource {
        return remoteDataSource
    }

    fun getCacheDataSource(): RecipeDataSource {
        return cacheDataSource
    }
}