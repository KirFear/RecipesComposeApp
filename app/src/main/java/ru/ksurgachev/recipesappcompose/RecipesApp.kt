package ru.ksurgachev.recipesappcompose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.ksurgachev.recipesappcompose.ui.categories.CategoriesScreen
import ru.ksurgachev.recipesappcompose.ui.favorites.FavoritesScreen
import ru.ksurgachev.recipesappcompose.ui.navigation.BottomNavigation
import ru.ksurgachev.recipesappcompose.ui.recipes.RecipesScreen
import ru.ksurgachev.recipesappcompose.ui.theme.RecipesAppComposeTheme

@Composable
fun RecipesApp() {
    RecipesAppComposeTheme {
        var currentScreen by remember { mutableStateOf(ScreenId.CATEGORIES) }
        var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
        var selectedCategoryTitle by remember { mutableStateOf("") }

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
                ScreenId.CATEGORIES -> CategoriesScreen(
                    Modifier.padding(paddingValues),
                    { categoryId, categoryTitle ->
                        selectedCategoryId = categoryId
                        selectedCategoryTitle = categoryTitle
                        currentScreen = ScreenId.RECIPES
                    }
                )

                ScreenId.FAVORITES -> FavoritesScreen(paddingValues)

                ScreenId.RECIPES -> RecipesScreen(
                    categoryId = selectedCategoryId ?: error("Category ID is required"),
                    categoryTitle = selectedCategoryTitle,
                    onRecipeClick = { recipeId ->
                        //TODO: переход на экран деталей рецепта
                    },
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesAppPreview() {
    RecipesApp()
}