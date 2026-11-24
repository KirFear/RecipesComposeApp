package ru.ksurgachev.recipesappcompose

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.ksurgachev.recipesappcompose.core.utils.Constants
import ru.ksurgachev.recipesappcompose.core.utils.Constants.DEEP_LINK_SCHEME
import ru.ksurgachev.recipesappcompose.data.repository.getRecipeById
import ru.ksurgachev.recipesappcompose.features.categories.ui.CategoriesScreen
import ru.ksurgachev.recipesappcompose.core.ui.ErrorMessage
import ru.ksurgachev.recipesappcompose.features.details.ui.RecipeDetailsScreen
import ru.ksurgachev.recipesappcompose.features.favorites.ui.FavoritesScreen
import ru.ksurgachev.recipesappcompose.ui.navigation.BottomNavigation
import ru.ksurgachev.recipesappcompose.features.recipes.ui.RecipesScreen
import ru.ksurgachev.recipesappcompose.features.recipes.presentation.model.toUiModel
import ru.ksurgachev.recipesappcompose.ui.theme.RecipesAppComposeTheme
import ru.ksurgachev.recipesappcompose.util.FavoriteDataStoreManager
import kotlin.text.toIntOrNull
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun RecipesApp(
    deepLinkIntent: Intent?
) {
    RecipesAppComposeTheme {
        val navController = rememberNavController()
        val context = LocalContext.current
        val favoriteManager = remember { FavoriteDataStoreManager(context) }
        val favoriteCountFlow = remember(favoriteManager) {
            favoriteManager.getFavoriteCountFlow()
        }
        val favoriteCount by favoriteCountFlow.collectAsState(initial = 0)

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
                    },
                    favoriteCount = favoriteCount
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
                        onCategoryClick = { categoryId, categoryTitle, categoryImageUrl ->
                            navController.navigate(
                                Destination.Recipes.createRoute(
                                    categoryId,
                                    categoryTitle,
                                    categoryImageUrl,
                                )
                            )
                        }
                    )
                }

                composable(Destination.Favorites.route) {
                    val favoriteRecipes by remember {
                        favoriteManager.getFavoriteIdsFlow()
                            .map { favoriteIds ->
                                val recipeIds = favoriteIds.mapNotNull { it.toIntOrNull() }
                                recipeIds.mapNotNull { id -> getRecipeById(id)?.toUiModel() }
                            }
                    }.collectAsState(initial = emptyList())

                    FavoritesScreen(
                        modifier = Modifier.padding(paddingValues),
                        favoriteRecipes = favoriteRecipes,
                        onRecipeClick = { recipeId ->
                            navController.navigate(Destination.Details.createRoute(recipeId))
                        }
                    )
                }

                composable(
                    route = Destination.Recipes.route,
                    arguments = listOf(
                        navArgument(Constants.KEY_CATEGORY_ID) { type = NavType.IntType },
                        navArgument(Constants.KEY_CATEGORY_TITLE) { type = NavType.StringType },
                        navArgument(Constants.KEY_CATEGORY_IMAGE_URL) { type = NavType.StringType },
                    )
                ) {
                    RecipesScreen(
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
                    val coroutineScope = rememberCoroutineScope()
                    val isFavoriteFlow = remember(recipeId) {
                        favoriteManager.isFavoriteFlow(recipeId)
                    }
                    val isFavorite by isFavoriteFlow.collectAsState(initial = false)

                    recipe?.let {
                        RecipeDetailsScreen(
                            recipe = it,
                            modifier = Modifier.padding(paddingValues),
                            isFavorite = isFavorite,
                            onFavoriteToggle = {
                                coroutineScope.launch {
                                    if (isFavorite) favoriteManager.removeFavorite(recipeId)
                                    else favoriteManager.addFavorite(recipeId)
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