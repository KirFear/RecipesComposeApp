package ru.ksurgachev.recipesappcompose.features.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ru.ksurgachev.recipe_app_compose.R
import ru.ksurgachev.recipesappcompose.data.repository.getRecipesByCategoryId
import ru.ksurgachev.recipesappcompose.core.ui.ScreenHeader
import ru.ksurgachev.recipesappcompose.features.recipes.presentation.model.RecipeUiModel
import ru.ksurgachev.recipesappcompose.features.recipes.presentation.model.toUiModel
import ru.ksurgachev.recipesappcompose.ui.theme.Dimens

@Composable
fun RecipesScreen(
    categoryId: Int?,
    categoryTitle: String,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var recipes by remember { mutableStateOf<List<RecipeUiModel>>(emptyList()) }

    LaunchedEffect(categoryId) {
        categoryId?.let {
            recipes = getRecipesByCategoryId(it).map { dto -> dto.toUiModel() }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        ScreenHeader(
            imagePainter = (painterResource(R.drawable.img_recipes_list)),
            contentDescription = categoryTitle,
            title = categoryTitle,
        )
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(Dimens.paddingMain),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingMain),
        ) {
            items(recipes, key = { it.id }) { item ->
                RecipeItem(
                    recipe = item,
                    onRecipeClick = onRecipeClick,
                    modifier = Modifier
                )
            }
        }
    }
}