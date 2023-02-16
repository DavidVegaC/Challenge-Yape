package com.davega.recetasyape.ui.recipelist

import android.widget.Filter
import com.davega.domain.models.Recipe
import java.lang.StringBuilder
import java.util.ArrayList

class RecipeFilter internal constructor(
    private val adapter: RecipeAdapter,
    recipeList: List<Recipe>
) : Filter() {

    private var recipes: LinkedHashMap<String, Recipe?> = LinkedHashMap()
    private var isFiltering = false
    private var lastFilter: CharSequence = ""

    init {
        fillContactsHashMap(recipeList)
    }

    fun setRecipeList(recipeNewList: List<Recipe>) {
        fillContactsHashMap(recipeNewList)
        filter(lastFilter)
    }

    private fun fillContactsHashMap(recipeNewList: List<Recipe>) {
        recipes = LinkedHashMap()
        populateLists(recipeNewList)
    }

    private fun populateLists(objects: List<Recipe>) = objects.forEach(this::addRecipe)

    private fun addRecipe(recipe: Recipe) {
        val id = recipe.id.toString()
        //if (recipes.containsKey(id)) return
        recipes[id] = recipe
    }

    override fun performFiltering(p0: CharSequence?): FilterResults {
        val results = FilterResults()
        if (p0 == null) return results

        lastFilter = p0
        if (p0.isNotEmpty()) {
            filterContacts(p0, results)
        } else {
            resetContactList(results)
        }
        return results
    }

    private fun resetContactList(results: FilterResults) = with(results) {
        count = recipes.size
        //Copy values to avoid modifying original lists
        val filteredContacts: MutableList<Recipe> = ArrayList()
        recipes.values.forEach { recipe -> if (recipe != null) { filteredContacts.add(recipe) }}
        values = filteredContacts
    }

    private fun filterContacts(p0: CharSequence, results: FilterResults) {
        isFiltering = true
        val filteredContacts: MutableList<Recipe> = ArrayList()
        recipes.values.forEach { recipe ->
            if (recipe != null && validateFilter(recipe, p0.toString())) {
                recipe.let(filteredContacts::add)
            }
        }
        results.run {
            count = filteredContacts.size
            values = filteredContacts
        }
    }

    private fun validateFilter(recipe: Recipe, p0: String) =
        recipe.title.uppercase().contains(p0, true) || convertIngredientListToString(recipe.ingredients).contains(p0, true)

    private fun convertIngredientListToString(ingredients: List<String>): String {
        val ingredientsString = StringBuilder()
        for (ingredient in ingredients) {
            ingredientsString.append("$ingredient;")
        }
        return ingredientsString.toString()
    }

    @Suppress("UNCHECKED_CAST")
    override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
        if(p1 == null) return
        val allegedContacts = p1.values
        adapter.updateData(allegedContacts as List<Recipe>, isFiltering)
        isFiltering = false
    }

}