package ru.ksurgachev.recipesappcompose.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.map
import ru.ksurgachev.recipe_app_compose.R
import ru.ksurgachev.recipesappcompose.data.repository.RecipesRepository
import ru.ksurgachev.recipesappcompose.ui.components.ScreenHeader
import ru.ksurgachev.recipesappcompose.ui.recipes.RecipeItem
import ru.ksurgachev.recipesappcompose.ui.recipes.model.toUiModel
import ru.ksurgachev.recipesappcompose.ui.theme.Dimens
import ru.ksurgachev.recipesappcompose.util.FavoriteDataStoreManager

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    favoriteManager: FavoriteDataStoreManager,
    recipesRepository: RecipesRepository,
    onRecipeClick: (Int) -> Unit
) {
    val favoriteRecipes by remember {
        favoriteManager.getFavoriteIdsFlow()
            .map { favoriteIds ->
                val recipeIds = favoriteIds.mapNotNull { it.toIntOrNull() }
                recipeIds.mapNotNull { id -> recipesRepository.getRecipeById(id)?.toUiModel() }
            }
    }.collectAsState(initial = emptyList())

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        ScreenHeader(
            imagePainter = (painterResource(R.drawable.img_favorites)),
            contentDescription = "Избранное",
            title = "Избранное",
        )
        if (favoriteRecipes.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(Dimens.paddingMain),
                verticalArrangement = Arrangement.spacedBy(Dimens.paddingMain),
            ) {
                items(favoriteRecipes, key = { it.id }) { item ->
                    RecipeItem(
                        recipe = item,
                        onRecipeClick = onRecipeClick,
                        modifier = Modifier
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimens.paddingMain),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Вы еще не добавили ни одного рецепта в избранное",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}