package com.davega.recetasyape.ui.recipelist

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.davega.recetasyape.base.BaseFragment
import com.davega.recetasyape.base.BaseViewModel
import com.davega.recetasyape.compose.recipelist.RecipeListContent
import com.davega.recetasyape.databinding.FragmentRecipeListBinding
import com.google.accompanist.themeadapter.material.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : BaseFragment<FragmentRecipeListBinding, BaseViewModel>() {

    override fun getViewBinding(): FragmentRecipeListBinding =
        FragmentRecipeListBinding.inflate(layoutInflater)

    override val viewModel: RecipeListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                // You're in Compose world!
                MdcTheme {
                    RecipeListContent(navController = findNavController(), modifier = Modifier.fillMaxSize(), recipeListViewModel = viewModel)
                }
            }
        }
    }
}