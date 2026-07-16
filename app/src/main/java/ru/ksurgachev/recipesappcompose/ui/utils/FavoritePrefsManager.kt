package ru.ksurgachev.recipesappcompose.ui.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import ru.ksurgachev.recipesappcompose.Constants.KEY_FAVORITES
import ru.ksurgachev.recipesappcompose.Constants.PREFS_NAME

class FavoritePrefsManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    fun isFavorite(recipeId: Int): Boolean {
        val currentFavorites = sharedPreferences.getStringSet(KEY_FAVORITES, emptySet())
        return currentFavorites?.contains(recipeId.toString()) ?: false
    }

    fun addToFavorites(recipeId: Int) {
        val currentFavorites = sharedPreferences.getStringSet(KEY_FAVORITES, emptySet())
        val updatedFavorites = currentFavorites?.toMutableSet() ?: mutableSetOf()

        updatedFavorites.add(recipeId.toString())

        sharedPreferences.edit {
            putStringSet(KEY_FAVORITES, updatedFavorites)
        }
    }

    fun removeFromFavorites(recipeId: Int) {
        val currentFavorites = sharedPreferences.getStringSet(KEY_FAVORITES, emptySet())
        val updatedFavorites = currentFavorites?.toMutableSet() ?: mutableSetOf()

        updatedFavorites.remove(recipeId.toString())

        sharedPreferences.edit {
            putStringSet(KEY_FAVORITES, updatedFavorites)
        }
    }

    fun getAllFavorites(): Set<String> {
        val currentFavorites = sharedPreferences.getStringSet(KEY_FAVORITES, emptySet())

        return currentFavorites.orEmpty()
    }
}