package ru.ksurgachev.recipesappcompose.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import ru.ksurgachev.recipesappcompose.Constants

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.DATA_STORE_NAME,
    produceMigrations = { context ->
        listOf(
            SharedPreferencesMigration(
                context = context,
                sharedPreferencesName = Constants.PREFS_NAME
            )
        )
    }
)

object PreferencesKeys {
    val FAVORITE_RECIPE_IDS = stringSetPreferencesKey(Constants.KEY_FAVORITES)
}