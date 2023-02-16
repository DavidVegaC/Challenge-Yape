package com.davega.cache.mapper

import com.davega.cache.models.RecipeCacheEntity
import com.davega.data.models.RecipeEntity
import java.lang.StringBuilder
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class RecipeCacheMapper @Inject constructor() : CacheMapper<RecipeCacheEntity, RecipeEntity> {
    override fun mapFromCached(type: RecipeCacheEntity): RecipeEntity {
        return RecipeEntity(
            id = type.id,
            title = type.title,
            urlImage = type.urlImage,
            publisher = type.publisher,
            ingredients = convertIngredientsToList(type.ingredients),
            instructions = convertIngredientsToList(type.instructions),
            location = convertIngredientsToList(type.location),
            dateAdded = Date(),
            dateUpdated = Date()
        )
    }

    override fun mapToCached(type: RecipeEntity): RecipeCacheEntity {
        return RecipeCacheEntity(
            id = type.id,
            title = type.title,
            urlImage = type.urlImage,
            publisher = type.publisher,
            ingredients = convertIngredientListToString(type.ingredients),
            instructions = convertIngredientListToString(type.instructions),
            location = convertIngredientListToString(type.location),
            dateAdded = "",
            dateUpdated = ""
        )
    }

    private fun convertIngredientListToString(ingredients: List<String>): String {
        val ingredientsString = StringBuilder()
        for (ingredient in ingredients) {
            ingredientsString.append("$ingredient;")
        }
        return ingredientsString.toString()
    }

    // "carrot, chicken, etc.."
    private fun convertIngredientsToList(ingredientString: String?): List<String> {
        val list: ArrayList<String> = ArrayList()
        ingredientString?.let {
            for (ingredient in it.split(";")) {
                list.add(ingredient)
            }
        }
        return list
    }
}