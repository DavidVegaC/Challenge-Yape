package com.davega.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.davega.cache.models.RecipeCacheEntity

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipes")
    fun getRecipes(): List<RecipeCacheEntity>

    @Query("SELECT * FROM recipes WHERE  id = :id")
    fun getRecipe(id: Long): RecipeCacheEntity

    @Query("DELETE FROM recipes")
    fun clearRecipes(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun addRecipe(vararg recipe: RecipeCacheEntity)
}