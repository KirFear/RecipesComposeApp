package ru.ksurgachev.recipesappcompose.util

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteDataStoreManager(context: Context) {
    val dataStore = context.dataStore

    fun getFavoriteIdsFlow(): Flow<Set<String>> {
        val preferencesFlow: Flow<Preferences> = dataStore.data

        return preferencesFlow.map { preferences ->
            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
        }
    }

    fun isFavoriteFlow(recipeId: Int): Flow<Boolean> {
        return getFavoriteIdsFlow().map { favoriteIds ->
            favoriteIds.contains(recipeId.toString())
        }
    }

    fun getFavoriteCountFlow(): Flow<Int> {
        return getFavoriteIdsFlow().map { it.size }
    }

    suspend fun addFavorite(recipeId: Int) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
            val updatedFavorites = currentFavorites + recipeId.toString()

            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] = updatedFavorites
        }
    }

    suspend fun removeFavorite(recipeId: Int) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
            val updatedFavorites = currentFavorites - recipeId.toString()

            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] = updatedFavorites
        }
    }
}