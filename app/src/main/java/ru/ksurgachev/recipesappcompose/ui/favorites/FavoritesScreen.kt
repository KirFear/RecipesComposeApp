package ru.ksurgachev.recipesappcompose.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.ksurgachev.recipesappcompose.Constants
import ru.ksurgachev.recipesappcompose.ui.components.ScreenHeader

@Composable
fun FavoritesScreen(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        ScreenHeader(
            imageUrl = Constants.ASSETS_URI_PREFIX + "bcg_favorites.png",
            contentDescription = "Избранное",
            title = "ИЗБРАННОЕ"
        )
    }
}