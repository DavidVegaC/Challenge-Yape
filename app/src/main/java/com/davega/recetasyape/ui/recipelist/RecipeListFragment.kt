package com.davega.recetasyape.ui.recipelist

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.davega.recetasyape.R
import com.davega.recetasyape.base.BaseFragment
import com.davega.recetasyape.base.BaseViewModel
import com.davega.recetasyape.databinding.FragmentRecipeListBinding
import com.davega.recetasyape.extension.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : BaseFragment<FragmentRecipeListBinding, BaseViewModel>() {

    override fun getViewBinding(): FragmentRecipeListBinding =
        FragmentRecipeListBinding.inflate(layoutInflater)

    override val viewModel: RecipeListViewModel by viewModels()

    @Inject
    lateinit var recipeAdapter: RecipeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRecipes()
        initUi()
    }

    private fun initUi(){
        setupObservers()
        setupRecyclerView()
        setupSearch()
        binding.srlRecipes.setOnRefreshListener {
            viewModel.getRecipes()
        }
    }

    private fun setupSearch() = with(binding.searchViewRecipes) {
        setOnQueryTextListener(queryTextListener())
        requestFocus()
    }

    private fun setupObservers(){
        observe(viewModel.getRecipeList(), ::onViewStateChange)
    }

    private fun queryTextListener(): SearchView.OnQueryTextListener = object: SearchView.OnQueryTextListener {

        override fun onQueryTextChange(newText: String): Boolean {
            filterContacts(newText)
            return true
        }

        override fun onQueryTextSubmit(query: String): Boolean {
            return false
        }

    }

    private fun filterContacts(searchParams: String) {
        recipeAdapter.filter.filter(searchParams)
    }

    private fun setupRecyclerView() {
        binding.recyclerViewRecipes.apply {
            adapter = recipeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        recipeAdapter.setItemClickListener { recipe ->
            val action: NavDirections = RecipeListFragmentDirections.actionRecipeListFragmentToRecipeDetailFragment(recipe.id)
            findNavController().navigate(action)
        }
    }

    private fun onViewStateChange(event: RecipeUIModel) {
        if (event.isRedelivered) return
        when (event) {
            is RecipeUIModel.Error -> {
                handleLoading(false)
                handleErrorMessage(event.error)
            }
            is RecipeUIModel.Loading -> handleLoading(true)
            is RecipeUIModel.Success -> {
                handleLoading(false)
                event.data.let {
                    recipeAdapter.list = it
                    recipeAdapter.updateFilterDataSet(it)
                }
                binding.srlRecipes.isRefreshing = false
            }
        }
    }

}