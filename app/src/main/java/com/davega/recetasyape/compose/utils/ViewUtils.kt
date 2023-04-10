package com.davega.recetasyape.compose.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

private const val SplashWaitTime: Long = 2000

@Composable
fun SnackbarUtils(
    message: String
) {
    var snackbarVisibleState by remember { mutableStateOf(true) }
    val currentOnTimeout by rememberUpdatedState{ snackbarVisibleState = false}
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Create an effect that matches the lifecycle of LandingScreen.
        // If LandingScreen recomposes or onTimeout changes,
        // the delay shouldn't start again.
        LaunchedEffect(true) {
            delay(SplashWaitTime)
            currentOnTimeout()
        }
        if(snackbarVisibleState){
            Snackbar(
                modifier = Modifier.padding(8.dp)
            ) { Text(text = message) }
        }
    }
}

@Composable
fun LoadingUtils(modifier: Modifier = Modifier){
    Box {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .matchParentSize()
                .background(Color.Transparent)
        ) {
            CircularProgressIndicator()
        }
    }
}