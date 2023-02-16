package com.davega.domain.interactor

import com.davega.domain.models.Recipe
import com.davega.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


typealias GetRecipeByNameBaseUseCase = BaseUseCase<String, Flow<List<Recipe>>>

class GetRecipeByNameUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) : GetRecipeByNameBaseUseCase {
    override suspend fun invoke(params: String): Flow<List<Recipe>> = recipeRepository.getRecipeByName(params)
}