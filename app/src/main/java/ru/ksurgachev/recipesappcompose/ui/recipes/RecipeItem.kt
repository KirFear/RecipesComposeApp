package ru.ksurgachev.recipesappcompose.ui.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import coil3.compose.AsyncImage
import ru.ksurgachev.recipe_app_compose.R
import ru.ksurgachev.recipesappcompose.ui.recipes.model.RecipeUiModel
import ru.ksurgachev.recipesappcompose.ui.theme.Dimens

@Composable
fun RecipeItem(
    recipe: RecipeUiModel,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onRecipeClick(recipe.id) },
        shape = RoundedCornerShape(Dimens.buttonCornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(),
    ) {
        Column {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.img_placeholder),
                error = painterResource(id = R.drawable.img_error)
            )

            Text(
                text = recipe.title.uppercase(),
                modifier = Modifier.padding(Dimens.paddingMain),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}