package ru.ksurgachev.recipesappcompose.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ru.ksurgachev.recipe_app_compose.R
import ru.ksurgachev.recipesappcompose.ui.components.ScreenHeader

@Composable
fun FavoritesScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        ScreenHeader(
            imagePainter = (painterResource(R.drawable.img_favorites)),
            contentDescription = "Избранное",
            title = "Избранное"
        )
    }
}