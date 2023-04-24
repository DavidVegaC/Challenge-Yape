package com.davega.recetasyape.ui.viewmodel.recipedetail

import androidx.lifecycle.LiveData
import com.davega.domain.interactor.GetRecipeByIdUseCase
import com.davega.domain.models.Recipe
import com.davega.recetasyape.base.BaseViewModel
import com.davega.recetasyape.utils.CoroutineContextProvider
import com.davega.recetasyape.utils.UiAwareLiveData
import com.davega.recetasyape.utils.UiAwareModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

sealed class RecipeDetailUIModel : UiAwareModel() {
    object Loading : RecipeDetailUIModel()
    data class Error(var error: String = "") : RecipeDetailUIModel()
    data class Success(val data: Recipe) : RecipeDetailUIModel()
}

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val recipeByIdUseCase: GetRecipeByIdUseCase
) : BaseViewModel(contextProvider) {

    private val _recipe = UiAwareLiveData<RecipeDetailUIModel>()
    private var recipe: LiveData<RecipeDetailUIModel> = _recipe

    fun getRecipe(): LiveData<RecipeDetailUIModel> {
        return recipe
    }

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _recipe.postValue(RecipeDetailUIModel.Error(exception.message ?: "Error"))
    }

    fun getRecipeDetail(recipeId: Long) {
        _recipe.postValue(RecipeDetailUIModel.Loading)
        launchCoroutineIO {
            loadRecipe(recipeId)
        }
    }

    private suspend fun loadRecipe(recipeId: Long) {
        recipeByIdUseCase(recipeId).collect {
            _recipe.postValue(RecipeDetailUIModel.Success(it))
        }
    }

}