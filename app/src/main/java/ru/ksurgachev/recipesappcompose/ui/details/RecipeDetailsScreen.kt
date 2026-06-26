package ru.ksurgachev.recipesappcompose.ui.details

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.ksurgachev.recipesappcompose.ui.recipes.model.RecipeUiModel
import ru.ksurgachev.recipesappcompose.ui.theme.Dimens

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel,
    modifier: Modifier = Modifier
) {
    Text(
        text = recipe.title,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier.padding(Dimens.paddingMain)
    )
}