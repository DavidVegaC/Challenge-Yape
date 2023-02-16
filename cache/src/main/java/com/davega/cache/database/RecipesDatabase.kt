package com.davega.cache.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.davega.cache.dao.RecipeDao
import com.davega.cache.models.RecipeCacheEntity
import com.davega.cache.utils.CacheConstants
import javax.inject.Inject

@Database(
    entities = [RecipeCacheEntity::class],
    version = CacheConstants.DB_VERSION,
    exportSchema = false
)
abstract class RecipesDatabase @Inject constructor() : RoomDatabase() {

    abstract fun cachedRecipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipesDatabase? = null

        fun getInstance(context: Context): RecipesDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RecipesDatabase::class.java,
            CacheConstants.DB_NAME
        ).build()
    }
}