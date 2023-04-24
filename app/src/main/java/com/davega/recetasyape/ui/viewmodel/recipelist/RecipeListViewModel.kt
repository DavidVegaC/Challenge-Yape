package com.davega.recetasyape.ui.viewmodel.recipelist

import androidx.lifecycle.LiveData
import com.davega.domain.interactor.GetRecipeListUseCase
import com.davega.domain.models.Recipe
import com.davega.recetasyape.base.BaseViewModel
import com.davega.recetasyape.utils.CoroutineContextProvider
import com.davega.recetasyape.utils.UiAwareLiveData
import com.davega.recetasyape.utils.UiAwareModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

sealed class RecipeUIModel : UiAwareModel() {
    object Loading : RecipeUIModel()
    data class Error(var error: String = "") : RecipeUIModel()
    data class Success(val data: List<Recipe>) : RecipeUIModel()
}

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val recipeListUseCase: GetRecipeListUseCase
) : BaseViewModel(contextProvider) {

    private val _recipeList = UiAwareLiveData<RecipeUIModel>()
    private var recipeList: LiveData<RecipeUIModel> = _recipeList

    fun getRecipeList(): LiveData<RecipeUIModel> {
        return recipeList
    }

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _recipeList.postValue(RecipeUIModel.Error(exception.message ?: "Error"))
    }

    fun getRecipes() {
        _recipeList.postValue(RecipeUIModel.Loading)
        launchCoroutineIO {
            loadRecipes()
        }
    }

    private suspend fun loadRecipes() {
        recipeListUseCase(Unit).collect {
            _recipeList.postValue(RecipeUIModel.Success(it))
        }
    }
}