package com.davega.recetasyape.compose.recipedetail

import android.annotation.SuppressLint
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.davega.recetasyape.ui.recipedetail.RecipeDetailFragmentDirections
import com.davega.recetasyape.ui.recipedetail.RecipeDetailUIModel
import com.davega.recetasyape.ui.recipedetail.RecipeDetailViewModel
import com.google.accompanist.themeadapter.material.MdcTheme


private val headerHeight = 275.dp

@Composable
fun CollapsingToolbar(onItemClick: (String, String, String) -> Unit, recipe: Recipe) {
    val scroll: ScrollState = rememberScrollState(0)
    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    Box(modifier = Modifier.fillMaxSize()) {
        Header(scroll, headerHeightPx, recipe.urlImage)
        Body(scroll, recipe, onItemClick)
    }
}

@Composable
private fun Header(scroll: ScrollState, headerHeightPx: Float, urlImage: String) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(headerHeight)
        .graphicsLayer {
            alpha = (-1f / headerHeightPx) * scroll.value + 1
        }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(urlImage)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.bg_splash_src),
            error = painterResource(R.drawable.bg_splash_src),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,
        )

        Box(
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xAA000000)),
                        startY = 3 * headerHeightPx / 4 // to wrap the title only
                    )
                )
        )
    }
}

@Composable
private fun TitleRecipe(title: String) {
    Card(
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h3.copy(fontSize = 20.sp),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 10.dp)
        )
    }
}

@Composable
private fun HeaderSubtitle(@StringRes title: Int) {
    Text(
        text = stringResource(id = title),
        style = MaterialTheme.typography.body1.copy(fontSize = 15.sp),
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp)
    )
}

@Composable
private fun BodyCard(cardText: String, @ColorRes color: Int) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        backgroundColor = colorResource(id = color)
    ) {
        Text(
            text = cardText,
            style = MaterialTheme.typography.body2.copy(fontSize = 13.sp),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 12.dp)
        )
    }
}

@Composable
private fun Body(
    scroll: ScrollState,
    recipe: Recipe,
    onItemClick: (String, String, String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(scroll)
    ) {
        Spacer(Modifier.height(headerHeight))

        TitleRecipe(recipe.title)
        HeaderSubtitle(R.string.title_ingredients)
        BodyCard(
            cardText = convertIngredientListToString(recipe.ingredients),
            color = R.color.colorCardview1
        )
        HeaderSubtitle(R.string.title_instructions)
        BodyCard(
            cardText = convertIngredientListToString(recipe.instructions),
            color = R.color.colorCardview2
        )
        Button(
            onClick = { onItemClick(recipe.location[0], recipe.location[1], recipe.title) },
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 10.dp)
        ) {
            Text(text = stringResource(id = R.string.text_to_map))
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RecipeDetailContent(
    idRecipe: Long,
    navController: NavController,
    modifier: Modifier = Modifier,
    recipeDetailViewModel: RecipeDetailViewModel = viewModel()
) {
    val responseRecipeDetail by recipeDetailViewModel.getRecipe().observeAsState()

    var isRecipeDetailLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        recipeDetailViewModel.getRecipeDetail(idRecipe)
    }

    if (isRecipeDetailLoading) {
        LoadingUtils()
    }

    when (responseRecipeDetail) {
        is RecipeDetailUIModel.Loading -> isRecipeDetailLoading = true
        is RecipeDetailUIModel.Error -> {
            isRecipeDetailLoading = false
            SnackbarUtils((responseRecipeDetail as RecipeDetailUIModel.Error).error)
        }
        is RecipeDetailUIModel.Success -> {
            isRecipeDetailLoading = false
            Column(
                modifier = modifier
            ) {
                CollapsingToolbar(
                    recipe = (responseRecipeDetail as RecipeDetailUIModel.Success).data,
                    onItemClick = { latFood, lngFood, nameFood ->
                        val action: NavDirections =
                            RecipeDetailFragmentDirections.actionRecipeDetailFragmentToMapsFragment(
                                latFood,
                                lngFood,
                                nameFood
                            )
                        navController.navigate(action)
                    })
            }
        }
        else -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun TitleRecipePreview() {
    MdcTheme {
        TitleRecipe("Papa a la Huancaína")
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailContentPreview() {
    val navController = rememberNavController()
    MdcTheme {
        RecipeDetailContent(navController = navController, idRecipe = 1)
    }
}

private fun convertIngredientListToString(ingredients: List<String>): String {
    val ingredientsString = StringBuilder()
    for (i in ingredients.indices) {
        if (ingredients[i] != "") ingredientsString.append("- ${ingredients[i]}")
        if (i != ingredients.size - 1) ingredientsString.append("\n")
    }
    return ingredientsString.toString()
}