package com.davega.data.mapper

import com.davega.data.models.RecipeEntity
import com.davega.domain.models.Recipe
import javax.inject.Inject

class RecipeMapper @Inject constructor() : Mapper<RecipeEntity, Recipe> {
    override fun mapFromEntity(type: RecipeEntity): Recipe {
        return Recipe(
            id = type.id,
            title = type.title,
            urlImage = type.urlImage,
            publisher = type.publisher,
            ingredients = type.ingredients,
            instructions = type.instructions,
            location = type.location,
            dateAdded = type.dateAdded,
            dateUpdated = type.dateUpdated
        )
    }

    override fun mapToEntity(type: Recipe): RecipeEntity {
        return RecipeEntity(
            id = type.id,
            title = type.title,
            urlImage = type.urlImage,
            publisher = type.publisher,
            ingredients = type.ingredients,
            instructions = type.instructions,
            location = type.location,
            dateAdded = type.dateAdded,
            dateUpdated = type.dateUpdated
        )
    }
}