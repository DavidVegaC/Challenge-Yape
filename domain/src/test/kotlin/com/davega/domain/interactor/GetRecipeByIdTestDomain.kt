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

class GetRecipeByIdTestDomain {

    @RelaxedMockK
    lateinit var recipeRepository: RecipeRepository

    lateinit var sut: GetRecipeByIdUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        sut = GetRecipeByIdUseCase(recipeRepository)
    }

    @Test
    fun `get recipe with id should return success result with recipe detail`() = runBlocking {
            // Arrange (Given)
            val recipeId = 1L
            coEvery { recipeRepository.getRecipe(recipeId) } returns FakeData.getRecipe()

            // Act (When)
            val recipe = sut(recipeId).single()

            // Assert (Then)
            TestCase.assertEquals(recipe.id, recipeId.toInt())
            TestCase.assertEquals(recipe.title, "Seco de Carne")
            coVerify(exactly = 1) {recipeRepository.getRecipe(recipeId)}
        }
}