package ru.ksurgachev.recipesappcompose

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ksurgachev.recipesappcompose.Constants.DEEP_LINK_SCHEME
import ru.ksurgachev.recipesappcompose.data.repository.getRecipeById
import ru.ksurgachev.recipesappcompose.ui.categories.CategoriesScreen
import ru.ksurgachev.recipesappcompose.ui.components.ErrorMessage
import ru.ksurgachev.recipesappcompose.ui.details.RecipeDetailsScreen
import ru.ksurgachev.recipesappcompose.ui.favorites.FavoritesScreen
import ru.ksurgachev.recipesappcompose.ui.navigation.BottomNavigation
import ru.ksurgachev.recipesappcompose.ui.recipes.RecipesScreen
import ru.ksurgachev.recipesappcompose.ui.recipes.model.toUiModel
import ru.ksurgachev.recipesappcompose.ui.theme.RecipesAppComposeTheme
import ru.ksurgachev.recipesappcompose.util.FavoriteDataStoreManager
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun RecipesApp(
    deepLinkIntent: Intent?
) {
    RecipesAppComposeTheme {
        val navController = rememberNavController()

        LaunchedEffect(deepLinkIntent) {
            deepLinkIntent?.data?.let { uri ->
                val recipeId: Int? = when (uri.scheme) {
                    DEEP_LINK_SCHEME ->
                        if (uri.pathSegments.size == 1 && uri.host == "recipe")
                            uri.pathSegments[0].toIntOrNull() else null

                    "https", "http" ->
                        if (uri.pathSegments.size == 2 && uri.pathSegments[0] == "recipe")
                            uri.pathSegments[1].toIntOrNull() else null

                    else -> null
                }

                if (recipeId != null) {
                    delay(100.milliseconds)
                    navController.navigate(Destination.Details.createRoute(recipeId))
                }
            }
        }

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
                        onRecipeClick = { recipeId ->
                            navController.navigate(Destination.Details.createRoute(recipeId))
                        },
                        modifier = Modifier.padding(paddingValues)
                    )
                }

                composable(
                    route = Destination.Details.route,
                    arguments = listOf(
                        navArgument(Constants.KEY_RECIPE_ID) { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val recipeId = backStackEntry.arguments?.getInt(Constants.KEY_RECIPE_ID) ?: 0
                    val recipe = getRecipeById(recipeId)?.toUiModel()
                    val context = LocalContext.current
                    val favoriteManager = remember { FavoriteDataStoreManager(context) }
                    val coroutineScope = rememberCoroutineScope()
                    var isFavorite by remember { mutableStateOf(false) }

                    LaunchedEffect(recipeId) {
                        isFavorite = favoriteManager.isFavorite(recipeId)
                    }

                    recipe?.let {
                        RecipeDetailsScreen(
                            recipe = it,
                            modifier = Modifier.padding(paddingValues),
                            isFavorite = isFavorite,
                            onFavoriteToggle = {
                                coroutineScope.launch {
                                    if (isFavorite) favoriteManager.removeFavorite(recipeId)
                                    else favoriteManager.addFavorite(recipeId)

                                    isFavorite = !isFavorite
                                }
                            }
                        )
                    } ?: run {
                        ErrorMessage(
                            message = "Рецепт не найден",
                            onDismiss = { navController.popBackStack() },
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }
            }
        }
    }
}