package com.davega.recetasyape.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.davega.recetasyape.compose.main.MainScreen
import com.davega.recetasyape.core.theme.ThemeUtils
import com.google.accompanist.themeadapter.material.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val splashViewModel: MainActivityViewModel by viewModels()

    @Inject
    lateinit var themeUtils: ThemeUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MdcTheme {
                MainScreen(themeUtils = themeUtils)
            }
        }
    }
}