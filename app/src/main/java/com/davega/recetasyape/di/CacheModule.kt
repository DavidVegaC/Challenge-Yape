package com.davega.recetasyape.di

import android.content.Context
import com.davega.cache.RecipeCacheImpl
import com.davega.cache.dao.RecipeDao
import com.davega.cache.database.RecipesDatabase
import com.davega.cache.utils.CachePreferencesHelper
import com.davega.data.repository.RecipeCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): RecipesDatabase {
        return RecipesDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideRecipeDao(recipesDatabase: RecipesDatabase): RecipeDao {
        return recipesDatabase.cachedRecipeDao()
    }

    @Provides
    @Singleton
    fun provideRecipeCache(recipeCache: RecipeCacheImpl): RecipeCache {
        return recipeCache
    }

    @Provides
    @Singleton
    fun providePreferenceHelper(@ApplicationContext context: Context): CachePreferencesHelper {
        return CachePreferencesHelper(context)
    }
}