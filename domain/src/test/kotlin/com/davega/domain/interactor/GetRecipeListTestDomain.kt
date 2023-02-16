package com.davega.domain.interactor

import com.davega.domain.fakes.FakeData
import com.davega.domain.repository.RecipeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetRecipeListTestDomain {

    @RelaxedMockK
    lateinit var recipeRepository: RecipeRepository

    lateinit var sut: GetRecipeListUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        sut = GetRecipeListUseCase(recipeRepository)
    }

    @Test
    fun `get recipe should return success result with recipe list`() = runBlocking {
        // Arrange (Given)
        coEvery { recipeRepository.getRecipes() } returns FakeData.getRecipes()

        // Act (When)
        val recipes = sut(Unit).single()

        // Assert (Then)
        TestCase.assertEquals(recipes.size, 2)
        coVerify(exactly = 1) { recipeRepository.getRecipes() }
    }
}