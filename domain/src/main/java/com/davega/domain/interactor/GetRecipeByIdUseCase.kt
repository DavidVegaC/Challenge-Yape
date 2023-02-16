package com.davega.domain.interactor

import com.davega.domain.models.Recipe
import com.davega.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GetRecipeByIdBaseUseCase = BaseUseCase<Long, Flow<Recipe>>

class GetRecipeByIdUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) : GetRecipeByIdBaseUseCase {

    override suspend operator fun invoke(params: Long) = recipeRepository.getRecipe(params)
}