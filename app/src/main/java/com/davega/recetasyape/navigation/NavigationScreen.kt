package com.davega.recetasyape.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.davega.recetasyape.R

sealed class Screen(
    val route: String,
    @StringRes val title: Int = R.string.app_name,
    @DrawableRes val navIcon: Int = R.drawable.ic_home,
    val objectName: List<String> = emptyList(),
    val objectPath: String = ""
) {

    object Home : Screen("home_screen")
    object Settings : Screen("settings_screen")

    object RecipeDetail :
        Screen("recipe_detail_screen", objectName = listOf("recipeId"), objectPath = "/{recipeId}")

    object RecipeOrigin :
        Screen("recipe_origin_screen", objectName = listOf("recipeLat","recipeLng","recipeName"), objectPath = "/{recipeLat}/{recipeLng}/{recipeName}")

    // Bottom Navigation
    object HomeNav : Screen("home_screen", title = R.string.menu_home, navIcon = R.drawable.ic_home)

    object SettingsNav : Screen("settings_screen", title = R.string.menu_settings, navIcon = R.drawable.ic_settings)

}