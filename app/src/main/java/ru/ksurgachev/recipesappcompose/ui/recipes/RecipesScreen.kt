package ru.ksurgachev.recipesappcompose.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import ru.ksurgachev.recipe_app_compose.R
import ru.ksurgachev.recipesappcompose.ui.components.ScreenHeader
import ru.ksurgachev.recipesappcompose.ui.theme.Dimens

@Composable
fun RecipesScreen(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        ScreenHeader(
            imagePainter = painterResource(id = R.drawable.img_recipes_list),
            contentDescription = "Рецепты",
            title = "РЕЦЕПТЫ"
        )
        Text(
            text = "Скоро здесь будет список рецептов",
            modifier = Modifier
                .padding(Dimens.paddingMedium),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}