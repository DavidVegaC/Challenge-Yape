package com.davega.domain.models

import java.util.*

data class Recipe(
    val id: Int,
    val title: String,
    val urlImage: String,
    val publisher: String,
    val ingredients: List<String> = listOf(),
    val instructions: List<String> = listOf(),
    val location: List<String> = listOf(),
    val dateAdded: Date,
    val dateUpdated: Date
)