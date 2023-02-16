package com.davega.recetasyape.ui.recipelist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.davega.domain.models.Recipe
import com.davega.recetasyape.base.BaseAdapter
import com.davega.recetasyape.databinding.ItemRecipeListBinding
import javax.inject.Inject

class RecipeAdapter @Inject constructor(
    private val glide: RequestManager
) : BaseAdapter<Recipe>(), Filterable {

    private var filter: RecipeFilter? = null

    private val diffCallback = object : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemRecipeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    inner class RecipeViewHolder(private val binding: ItemRecipeListBinding) :
        RecyclerView.ViewHolder(binding.root), Binder<Recipe> {
        override fun bind(item: Recipe) {
            binding.apply {
                textViewRecipeName.text = item.title
                textViewPublisher.text = item.publisher
                glide.load(item.urlImage).into(imageViewRecipe)
                root.setOnClickListener {
                    onItemClickListener?.let { itemClick ->
                        itemClick(item)
                    }
                }
            }
        }
    }

    override fun getFilter(): Filter {
        if (filter == null) {
            filter = RecipeFilter(this, list)
        }
        return filter!!
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(recipeList: List<Recipe>, isFiltering: Boolean) {
        list = recipeList
        notifyDataSetChanged()
    }

    fun updateFilterDataSet(lisRecipes: List<Recipe>) = filter?.setRecipeList(lisRecipes)
}
