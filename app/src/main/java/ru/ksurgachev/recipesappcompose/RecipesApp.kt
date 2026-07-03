package ru.ksurgachev.recipesappcompose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.ksurgachev.recipesappcompose.ui.categories.CategoriesScreen
import ru.ksurgachev.recipesappcompose.ui.details.RecipeDetailsScreen
import ru.ksurgachev.recipesappcompose.ui.favorites.FavoritesScreen
import ru.ksurgachev.recipesappcompose.ui.navigation.BottomNavigation
import ru.ksurgachev.recipesappcompose.ui.recipes.RecipesScreen
import ru.ksurgachev.recipesappcompose.ui.recipes.model.RecipeUiModel
import ru.ksurgachev.recipesappcompose.ui.theme.RecipesAppComposeTheme

@Composable
fun RecipesApp() {
    RecipesAppComposeTheme {
        val navController = rememberNavController()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = {
                        navController.navigate(Destination.Categories.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onFavoriteClick = {
                        navController.navigate(Destination.Favorites.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Destination.Categories.route
            ) {
                composable(Destination.Categories.route) {
                    CategoriesScreen(
                        modifier = Modifier.padding(paddingValues),
                        onCategoryClick = { categoryId, categoryTitle ->
                            navController.navigate(
                                Destination.Recipes.createRoute(
                                    categoryId,
                                    categoryTitle
                                )
                            )
                        }
                    )
                }

                composable(Destination.Favorites.route) {
                    FavoritesScreen(modifier = Modifier.padding(paddingValues))
                }

                composable(
                    route = Destination.Recipes.route,
                    arguments = listOf(
                        navArgument(Constants.KEY_CATEGORY_ID) { type = NavType.IntType },
                        navArgument(Constants.KEY_CATEGORY_TITLE) { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val categoryId =
                        backStackEntry.arguments?.getInt(Constants.KEY_CATEGORY_ID) ?: 0
                    val categoryTitle =
                        backStackEntry.arguments?.getString(Constants.KEY_CATEGORY_TITLE) ?: ""

                    RecipesScreen(
                        categoryId = categoryId,
                        categoryTitle = categoryTitle,
                        onRecipeClick = { recipeId, recipe ->
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                Constants.KEY_RECIPE_OBJECT,
                                recipe
                            )
                            navController.navigate(Destination.Details.createRoute(recipeId))
                        },
                        modifier = Modifier.padding(paddingValues)
                    )
                }

                composable(Destination.Details.route) {
                    val recipe = navController.previousBackStackEntry
                        ?.savedStateHandle?.get<RecipeUiModel>(
                            Constants.KEY_RECIPE_OBJECT
                        ) ?: return@composable

                    RecipeDetailsScreen(
                        recipe = recipe,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesAppPreview() {
    RecipesApp()
}