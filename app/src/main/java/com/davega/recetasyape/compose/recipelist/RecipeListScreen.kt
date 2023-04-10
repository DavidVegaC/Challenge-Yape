package com.davega.recetasyape.compose.recipelist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.davega.domain.models.Recipe
import com.davega.recetasyape.R
import com.davega.recetasyape.compose.utils.LoadingUtils
import com.davega.recetasyape.compose.utils.SnackbarUtils
import com.davega.recetasyape.ui.recipelist.RecipeListFragmentDirections
import com.davega.recetasyape.ui.recipelist.RecipeListViewModel
import com.davega.recetasyape.ui.recipelist.RecipeUIModel
import com.google.accompanist.themeadapter.material.MdcTheme
import java.util.*

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = colorResource(id = R.color.search_view),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun RecipeListItem(recipe: Recipe, onItemClick: (Int) -> Unit, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        modifier = modifier
            .clickable(onClick = { onItemClick(recipe.id) })
            .height(100.dp)
            .padding(horizontal = 17.dp)
            .padding(top = 9.dp, bottom = 3.dp)
            .fillMaxWidth()
    ){
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(recipe.urlImage)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.bg_splash_src),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier.padding(start = 5.dp)
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.h3
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 5.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Text(
                        text = recipe.publisher,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                }
            }
        }
    }


}

@Composable
fun RecipeList(
    navController: NavController,
    listRecipe: List<Recipe>,
    state: MutableState<TextFieldValue>
) {
    var filteredRecipes: List<Recipe>
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        val searchedText = state.value.text
        filteredRecipes = if (searchedText.isEmpty()) {
            listRecipe
        } else {
            val resultList = ArrayList<Recipe>()
            for (recipe in listRecipe) {
                if (recipe.title.uppercase().contains(searchedText, true)
                ) {
                    resultList.add(recipe)
                }
            }
            resultList
        }
        items(filteredRecipes) { filteredRecipe ->
            RecipeListItem(
                recipe = filteredRecipe,
                onItemClick = { selectedRecipe ->
                    /* Add code later */
                    val action: NavDirections =
                        RecipeListFragmentDirections.actionRecipeListFragmentToRecipeDetailFragment(
                            selectedRecipe
                        )
                    navController.navigate(action)
                }
            )
        }
    }
}

@Composable
fun RecipeListContent(
    navController: NavController,
    modifier: Modifier = Modifier,
    recipeListViewModel: RecipeListViewModel = viewModel()
) {
    val responseRecipeList by recipeListViewModel.getRecipeList().observeAsState()

    var isRecipeListLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        recipeListViewModel.getRecipes()
    }

    val textState = remember { mutableStateOf(TextFieldValue("")) }

    if (isRecipeListLoading) {
        LoadingUtils()
    }

    when (responseRecipeList) {
        is RecipeUIModel.Loading -> isRecipeListLoading = true
        is RecipeUIModel.Error -> {
            isRecipeListLoading = false
            SnackbarUtils((responseRecipeList as RecipeUIModel.Error).error)
        }
        is RecipeUIModel.Success -> {
            isRecipeListLoading = false
            Column(
                modifier = modifier
            ) {
                SearchView(textState)
                RecipeList(
                    navController = navController,
                    listRecipe = (responseRecipeList as RecipeUIModel.Success).data,
                    state = textState
                )
            }
        }
        else -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    SearchView(textState)
}

@Preview(showBackground = true)
@Composable
fun RecipeListItemPreview() {
    MdcTheme {
        RecipeListItem(
            recipe = Recipe(
                1,
                "Arroz a la cubana",
                "",
                "David Vega",
                emptyList(),
                emptyList(),
                emptyList(),
                Date(),
                Date()
            ), onItemClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeListPreview() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val navController = rememberNavController()
    MdcTheme {
        RecipeList(
            navController = navController, listRecipe = listOf(
                Recipe(
                    1,
                    "Arroz a la cubana",
                    "",
                    "David Vega",
                    emptyList(),
                    emptyList(),
                    emptyList(),
                    Date(),
                    Date()
                ),
                Recipe(
                    2,
                    "Ceviche",
                    "",
                    "Julian Alvarez",
                    emptyList(),
                    emptyList(),
                    emptyList(),
                    Date(),
                    Date()
                ),
            ), state = textState
        )
    }
}