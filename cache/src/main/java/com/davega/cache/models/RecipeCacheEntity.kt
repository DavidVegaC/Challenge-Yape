package com.davega.cache.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.davega.cache.utils.CacheConstants

@Entity(tableName = CacheConstants.RECIPE_TABLE_NAME)
data class RecipeCacheEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val urlImage: String,
    val publisher: String,
    val ingredients: String,
    val instructions: String,
    val location: String,
    val dateAdded: String,
    val dateUpdated: String
)