package ru.ksurgachev.recipesappcompose

import android.net.Uri

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Favorites : Destination("favorites")
    object Recipes : Destination("recipes/{categoryId}/{categoryTitle}") {
        fun createRoute(categoryId: Int, categoryTitle: String) =
            "recipes/$categoryId/${Uri.encode(categoryTitle)}"
    }

    object Details : Destination("recipe/{recipeId}") {
        fun createRoute(recipeId: Int) =
            "recipe/$recipeId"
    }
}