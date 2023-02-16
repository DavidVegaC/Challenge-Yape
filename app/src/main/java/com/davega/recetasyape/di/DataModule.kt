package com.davega.recetasyape.di

import com.davega.data.RecipeRepositoryImp
import com.davega.data.SettingsRepositoryImp
import com.davega.domain.repository.RecipeRepository
import com.davega.domain.repository.SettingsRepository
import com.davega.recetasyape.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRecipeRepository(recipeRepository: RecipeRepositoryImp): RecipeRepository =
        recipeRepository

    @Provides
    @Singleton
    fun provideSettingRepository(): SettingsRepository =
        SettingsRepositoryImp(BuildConfig.VERSION_NAME)
}