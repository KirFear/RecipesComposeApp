package ru.ksurgachev.recipesappcompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.ksurgachev.recipesappcompose.ui.navigation.BottomNavigation
import ru.ksurgachev.recipesappcompose.ui.theme.Dimens
import ru.ksurgachev.recipesappcompose.ui.theme.RecipesAppComposeTheme

@Composable
fun RecipesApp() {
    RecipesAppComposeTheme {
        var currentScreen by remember { mutableStateOf(ScreenId.CATEGORIES) }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigation(
                    { currentScreen = ScreenId.CATEGORIES },
                    { currentScreen = ScreenId.FAVORITES }
                )
            }
        ) { paddingValues ->

            when (currentScreen) {
                ScreenId.CATEGORIES -> CategoriesScreen(paddingValues)
                ScreenId.FAVORITES -> FavoritesScreen(paddingValues)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesAppPreview() {
    RecipesApp()
}

@Composable
fun CategoriesScreen(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.cardHeight)
                .padding(Dimens.paddingSmall),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Категории",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(Dimens.paddingMedium)
            )
        }
    }
}

@Composable
fun FavoritesScreen(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.cardHeight)
                .padding(Dimens.paddingSmall),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Избранное",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(Dimens.paddingMedium)
            )
        }
    }
}