package com.davega.recetasyape.di

import androidx.viewbinding.BuildConfig
import com.davega.data.repository.RecipeRemote
import com.davega.remote.api.RecipeService
import com.davega.remote.api.ServiceFactory
import com.davega.remote.repository.RecipeRemoteImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideBlogService(): RecipeService {
        return ServiceFactory.create(BuildConfig.DEBUG, "https://63e87ffa5f3e35d898f15901.mockapi.io/api/v1/")
    }

    @Provides
    @Singleton
    fun provideRecipeRemote(recipeRemote: RecipeRemoteImpl): RecipeRemote {
        return recipeRemote
    }
}