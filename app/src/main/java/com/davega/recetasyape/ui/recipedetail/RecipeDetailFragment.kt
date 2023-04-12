package com.davega.recetasyape.ui.recipedetail

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.davega.recetasyape.base.BaseFragment
import com.davega.recetasyape.base.BaseViewModel
import com.davega.recetasyape.compose.recipedetail.RecipeDetailContent
import com.davega.recetasyape.databinding.FragmentRecipeDetailBinding
import com.google.accompanist.themeadapter.material.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailFragment : BaseFragment<FragmentRecipeDetailBinding, BaseViewModel>()  {

    override fun getViewBinding(): FragmentRecipeDetailBinding =
        FragmentRecipeDetailBinding.inflate(layoutInflater)

    override val viewModel: RecipeDetailViewModel by viewModels()

    private val args: RecipeDetailFragmentArgs by navArgs()

    //getArgs
    private var idRecipe: Long = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupArgs()
        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                // You're in Compose world!
                MdcTheme {
                    RecipeDetailContent(idRecipe = idRecipe, navController = findNavController(), modifier = Modifier.fillMaxSize(), recipeDetailViewModel = viewModel)
                }
            }
        }
    }

    private fun setupArgs(){
       idRecipe = args.idRecipe.toLong()
    }

}