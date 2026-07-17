package ru.ksurgachev.recipesappcompose.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import ru.ksurgachev.recipesappcompose.Constants

class FavoritePrefsManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        Constants.PREFS_NAME,
        Context.MODE_PRIVATE
    )

    fun isFavorite(recipeId: Int): Boolean {
        val currentFavorites = sharedPreferences.getStringSet(Constants.KEY_FAVORITES, emptySet())
        return currentFavorites?.contains(recipeId.toString()) ?: false
    }

    fun addToFavorites(recipeId: Int) {
        val currentFavorites = sharedPreferences.getStringSet(Constants.KEY_FAVORITES, emptySet())
        val updatedFavorites = currentFavorites?.toMutableSet() ?: mutableSetOf()

        updatedFavorites.add(recipeId.toString())

        sharedPreferences.edit {
            putStringSet(Constants.KEY_FAVORITES, updatedFavorites)
        }
    }

    fun removeFromFavorites(recipeId: Int) {
        val currentFavorites = sharedPreferences.getStringSet(Constants.KEY_FAVORITES, emptySet())
        val updatedFavorites = currentFavorites?.toMutableSet() ?: mutableSetOf()

        updatedFavorites.remove(recipeId.toString())

        sharedPreferences.edit {
            putStringSet(Constants.KEY_FAVORITES, updatedFavorites)
        }
    }

    fun getAllFavorites(): Set<String> {
        val currentFavorites = sharedPreferences.getStringSet(Constants.KEY_FAVORITES, emptySet())

        return currentFavorites.orEmpty()
    }
}