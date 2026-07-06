package ru.ksurgachev.recipesappcompose.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.placeholder
import ru.ksurgachev.recipe_app_compose.R
import ru.ksurgachev.recipesappcompose.ui.components.ScreenHeader
import ru.ksurgachev.recipesappcompose.ui.recipes.model.IngredientUiModel
import ru.ksurgachev.recipesappcompose.ui.recipes.model.RecipeUiModel
import ru.ksurgachev.recipesappcompose.ui.theme.Dimens

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.paddingMain)
            .verticalScroll(rememberScrollState())
    ) {
        ScreenHeader(
            imagePainter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(recipe.imageUrl)
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_error)
                    .build()
            ),
            contentDescription = recipe.title,
            title = recipe.title,
        )

        Text(
            text = "Ингредиенты".uppercase(),
            modifier = Modifier
                .padding(Dimens.paddingMain),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displayLarge
        )

        recipe.ingredients.forEachIndexed { index, ingredient ->
            IngredientItem(ingredient)
            if (index < recipe.ingredients.lastIndex) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            }
        }

        Text(
            text = "Способ приготовления".uppercase(),
            modifier = Modifier
                .padding(Dimens.paddingMain),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displayLarge
        )

        Column(
            modifier = Modifier.padding(Dimens.paddingMain)
        ) {
            recipe.method.forEachIndexed { index, method ->
                Text(
                    text = "${index + 1}. $method",
                    modifier = Modifier
                        .padding(Dimens.paddingMain, Dimens.paddingMedium),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge
                )
                if (index < recipe.method.lastIndex) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                }
            }
        }
    }
}

@Composable
fun IngredientItem(
    ingredient: IngredientUiModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.paddingMain, Dimens.paddingMedium),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = ingredient.name.uppercase(),
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "${ingredient.quantity.uppercase()} ${ingredient.unitOfMeasure.uppercase()}",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}