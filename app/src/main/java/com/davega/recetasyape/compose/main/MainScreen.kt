package com.davega.recetasyape.compose.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.davega.recetasyape.compose.component.appbar.AppBarWithArrow
import com.davega.recetasyape.compose.component.appbar.HomeAppBar
import com.davega.recetasyape.core.theme.ThemeUtils
import com.davega.recetasyape.navigation.Navigation
import com.davega.recetasyape.navigation.Screen
import com.davega.recetasyape.navigation.currentRoute
import com.davega.recetasyape.navigation.navigationTitle

@Composable
fun MainScreen(themeUtils : ThemeUtils) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            when (currentRoute(navController)) {
                Screen.Home.route, Screen.Settings.route -> {
                    val appTitle: String = navigationTitle(navController)
                    HomeAppBar(title = appTitle)
                }
                else -> {
                    AppBarWithArrow(navigationTitle(navController)) {
                        navController.popBackStack()
                    }
                }
            }
        },
        bottomBar = {
        when (currentRoute(navController)) {
            Screen.Home.route, Screen.Settings.route-> {
                BottomNavigationUI(navController)
            }
        }
    }) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Navigation(navController, Modifier.padding(it), themeUtils)
        }
    }
}

@Composable
fun BottomNavigationUI(navController: NavController) {
    BottomNavigation {
        val items = listOf(
            Screen.HomeNav,
            Screen.SettingsNav,
        )
        items.forEach { item ->
            BottomNavigationItem(
                label = { Text(text = stringResource(id = item.title)) },
                selected = currentRoute(navController) == item.route,
                icon = {
                    Icon(painter = painterResource(id = item.navIcon),
                        contentDescription = null, modifier = Modifier
                            .size(27.dp)
                            .padding(bottom = 2.dp))
                },
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                })
        }
    }
}