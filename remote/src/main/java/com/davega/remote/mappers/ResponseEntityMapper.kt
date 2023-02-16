package com.davega.remote.mappers

import com.davega.data.models.RecipeEntity
import com.davega.remote.models.RecipeModel
import java.util.*
import javax.inject.Inject

class RecipeEntityMapper @Inject constructor() : EntityMapper<RecipeModel, RecipeEntity> {
    override fun mapFromModel(model: RecipeModel): RecipeEntity {
        return RecipeEntity(
            id = model.id,
            title = model.title,
            urlImage = model.urlImage,
            publisher = model.publisher,
            ingredients = model.ingredients,
            instructions = model.instructions,
            location = model.location,
            dateAdded = Date(),
            dateUpdated = Date()
        )
    }
}