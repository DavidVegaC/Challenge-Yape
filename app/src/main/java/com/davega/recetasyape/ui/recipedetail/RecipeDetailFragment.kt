package com.davega.recetasyape.ui.recipedetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.davega.recetasyape.base.BaseFragment
import com.davega.recetasyape.base.BaseViewModel
import com.davega.recetasyape.databinding.FragmentRecipeDetailBinding
import com.davega.recetasyape.extension.observe
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder
import javax.inject.Inject

@AndroidEntryPoint
class RecipeDetailFragment : BaseFragment<FragmentRecipeDetailBinding, BaseViewModel>()  {

    override fun getViewBinding(): FragmentRecipeDetailBinding =
        FragmentRecipeDetailBinding.inflate(layoutInflater)

    override val viewModel: RecipeDetailViewModel by viewModels()

    @Inject
    lateinit var glide: RequestManager

    private val args: RecipeDetailFragmentArgs by navArgs()

    //getArgs
    private var idRecipe: Long = 1

    //send Args
    private var latFood: String = ""
    private var lngFood: String = ""
    private var nameFood: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupArgs()
        setupUi()
        viewModel.getRecipeDetail(idRecipe)
    }

    private fun setupArgs(){
       idRecipe = args.idRecipe.toLong()
    }

    private fun setupUi(){
        setupObservers()
        setupListeners()
    }

    private fun setupObservers(){
        observe(viewModel.getRecipe(), ::onViewStateChange)
    }

    private fun setupListeners(){
        binding.btnMap.setOnClickListener {
            val action: NavDirections = RecipeDetailFragmentDirections.actionRecipeDetailFragmentToMapsFragment(latFood, lngFood, nameFood)
            findNavController().navigate(action)
        }
    }

    private fun onViewStateChange(result: RecipeDetailUIModel) {
        if (result.isRedelivered) return
        when (result) {
            is RecipeDetailUIModel.Error -> handleErrorMessage(result.error)
            is RecipeDetailUIModel.Loading -> handleLoading(true)
            is RecipeDetailUIModel.Success -> {
                handleLoading(false)
                result.data.let { recipe ->
                    latFood = recipe.location[0]
                    lngFood = recipe.location[1]
                    nameFood = recipe.title
                    binding.apply {
                        tvTitle.text = recipe.title
                        glide.load(recipe.urlImage).into(imgItem)
                        tvIngredients.text = convertIngredientListToString(recipe.ingredients)
                        tvInstructions.text = convertIngredientListToString(recipe.instructions)
                    }
                }
            }
        }
    }

    private fun convertIngredientListToString(ingredients: List<String>): String {
        val ingredientsString = StringBuilder()
        for (i in ingredients.indices) {
            if(ingredients[i] != "") ingredientsString.append("- ${ingredients[i]}")
            if(i != ingredients.size-1)  ingredientsString.append("\n")
        }
        return ingredientsString.toString()
    }

}