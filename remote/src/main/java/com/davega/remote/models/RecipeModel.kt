package com.davega.remote.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class RecipeModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("url_image")
    val urlImage: String,
    @SerializedName("publisher")
    val publisher: String,
    @SerializedName("ingredients")
    val ingredients: List<String>,
    @SerializedName("instructions")
    val instructions: List<String>,
    @SerializedName("location")
    val location: List<String>,
    val dateAdded: Date,
    val dateUpdated: Date
)