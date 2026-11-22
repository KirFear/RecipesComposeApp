package ru.ksurgachev.recipesappcompose

import android.net.Uri
import ru.ksurgachev.recipesappcompose.core.utils.Constants.DEEP_LINK_BASE_URL

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Favorites : Destination("favorites")
    object Recipes : Destination("recipes/{categoryId}/{categoryTitle}/{categoryImageUrl}") {
        fun createRoute(categoryId: Int, categoryTitle: String, categoryImageUrl: String) =
            "recipes/$categoryId/${Uri.encode(categoryTitle)}/${Uri.encode(categoryImageUrl)}"
    }
    object Details : Destination("recipe/{recipeId}") {
        fun createRoute(recipeId: Int) =
            "recipe/$recipeId"
    }
    companion object {
        fun createRecipeDeepLink(recipeId: Int) =
            "$DEEP_LINK_BASE_URL/recipe/$recipeId"
    }
}
