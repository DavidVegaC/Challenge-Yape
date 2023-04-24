package com.davega.recetasyape.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.davega.recetasyape.R
import com.davega.recetasyape.compose.maps.DetailsContent
import com.davega.recetasyape.compose.recipedetail.RecipeDetailContent
import com.davega.recetasyape.compose.recipelist.RecipeListContent
import com.davega.recetasyape.compose.settings.SettingsContent
import com.davega.recetasyape.core.theme.ThemeUtils

@Composable
fun Navigation(
    navController: NavHostController, modifier: Modifier, themeUtils: ThemeUtils
) {
    NavHost(navController, startDestination = Screen.Home.route, modifier) {
        composable(Screen.Home.route) {
            RecipeListContent(
                navController = navController
            )
        }
        composable(Screen.Settings.route) {
            SettingsContent(themeUtils)
        }

        composable(
            Screen.RecipeDetail.route.plus(Screen.RecipeDetail.objectPath),
            arguments = listOf(navArgument(Screen.RecipeDetail.objectName[0]) {
                type = NavType.LongType
            })
        ) {
            label = stringResource(R.string.title_detail)
            val recipeId = it.arguments?.getLong(Screen.RecipeDetail.objectName[0])
            recipeId?.let {
                RecipeDetailContent(
                    navController = navController,
                    idRecipe = recipeId
                )
            }
        }

        composable(
            Screen.RecipeOrigin.route.plus(Screen.RecipeOrigin.objectPath),
            arguments = listOf(
                navArgument(Screen.RecipeOrigin.objectName[0]) { NavType.StringType },
                navArgument(Screen.RecipeOrigin.objectName[1]) { NavType.StringType },
                navArgument(Screen.RecipeOrigin.objectName[2]) { NavType.StringType }
            )
        ) {
            label = stringResource(R.string.title_map)
            val recipeLat = it.arguments?.getString(Screen.RecipeOrigin.objectName[0])
            val recipeLng = it.arguments?.getString(Screen.RecipeOrigin.objectName[1])
            val recipeName = it.arguments?.getString(Screen.RecipeOrigin.objectName[2])

            DetailsContent(
                recipeLat?.toDouble() ?: 0.0,
                recipeLng?.toDouble() ?: 0.0,
                recipeName ?: ""
            )
        }
    }
}

@Composable
fun navigationTitle(navController: NavController): String {
    return when (currentRoute(navController)) {
        Screen.Home.route -> stringResource(id = R.string.title_home)
        Screen.RecipeDetail.route -> stringResource(id = R.string.title_detail)
        Screen.RecipeOrigin.route -> stringResource(id = R.string.title_map)
        Screen.Settings.route -> stringResource(id = R.string.title_settings)
        else -> {
            "holi"
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBefore("/")
}