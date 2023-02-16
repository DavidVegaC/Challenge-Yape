package com.davega.domain.interactor

import com.davega.domain.models.Recipe
import com.davega.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GetRecipeListBaseUseCase = BaseUseCase<Unit, Flow<List<Recipe>>>

class GetRecipeListUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) : GetRecipeListBaseUseCase {

    override suspend operator fun invoke(params: Unit) = recipeRepository.getRecipes()
}