package com.davega.recetasyape.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.davega.recetasyape.R
import com.davega.recetasyape.core.theme.ThemeUtils
import com.davega.recetasyape.core.theme.ThemeUtilsImp
import com.davega.recetasyape.utils.CoroutineContextProvider
import com.davega.recetasyape.utils.CoroutineContextProviderImp
import com.davega.recetasyape.utils.PresentationPreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

    @Singleton
    @Provides
    fun bindThemeUtils(themeUtilsImp: ThemeUtilsImp): ThemeUtils = themeUtilsImp

    @Provides
    @Singleton
    fun providePresentationPreferenceHelper(@ApplicationContext context: Context): PresentationPreferencesHelper {
        return PresentationPreferencesHelper(context)
    }

    @Provides
    @Singleton
    fun provideCoroutineContextProvider(contextProvider: CoroutineContextProviderImp): CoroutineContextProvider =
        contextProvider
}