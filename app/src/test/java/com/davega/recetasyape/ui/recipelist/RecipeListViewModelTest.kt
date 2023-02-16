package com.davega.recetasyape.ui.recipelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.davega.domain.interactor.GetRecipeListUseCase
import com.davega.fakes.FakeData
import com.davega.fakes.flattenToList
import com.davega.recetasyape.utils.CoroutineContextProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RecipeListViewModelTest{

     @RelaxedMockK
     private lateinit var getRecipeListUseCase: GetRecipeListUseCase

     @RelaxedMockK
     private lateinit var contextProvider: CoroutineContextProvider

     lateinit var recipeListViewModel: RecipeListViewModel

     @get:Rule
     var rule:InstantTaskExecutorRule = InstantTaskExecutorRule()

     @Before
     fun onBefore() {
         MockKAnnotations.init(this)
         recipeListViewModel = RecipeListViewModel(contextProvider, getRecipeListUseCase)
         Dispatchers.setMain(Dispatchers.Unconfined)
     }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is created at the first time, get all recipes and set the first value`() = runTest{
        //Given
        coEvery { getRecipeListUseCase(Unit) } returns FakeData.recipes

        //When
        recipeListViewModel.getRecipes()

        //Then
        assert(recipeListViewModel.getRecipeList().value == RecipeUIModel.Success(FakeData.recipes.flattenToList()))
    }


}