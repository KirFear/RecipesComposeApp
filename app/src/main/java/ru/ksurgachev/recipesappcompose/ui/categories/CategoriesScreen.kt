package ru.ksurgachev.recipesappcompose.ui.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ru.ksurgachev.recipe_app_compose.R
import ru.ksurgachev.recipesappcompose.ui.components.ScreenHeader

@Composable
fun CategoriesScreen(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        ScreenHeader(
            imagePainter = painterResource(id = R.drawable.img_categories),
            contentDescription = "Категории",
            title = "КАТЕГОРИИ"
        )
    }
}