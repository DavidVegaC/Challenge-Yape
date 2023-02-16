package com.davega.remote.api

import com.davega.remote.models.RecipeModel
import com.davega.remote.models.RecipeResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeService {
    @GET("recipes")
    suspend fun getRecipes(): List<RecipeModel>

    @GET("recipes/{id}")
    suspend fun getRecipe(@Path("id") id: Long): RecipeModel
}