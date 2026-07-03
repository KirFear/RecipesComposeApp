package ru.ksurgachev.recipesappcompose.ui.recipes.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import ru.ksurgachev.recipesappcompose.Constants
import ru.ksurgachev.recipesappcompose.data.model.RecipeDto

@Immutable
@Parcelize
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientUiModel>,
    val method: List<String>,
    val imageUrl: String,
    val isFavorite: Boolean
) : Parcelable

fun RecipeDto.toUiModel() = RecipeUiModel(
    id = id,
    title = title,
    ingredients = ingredients.map { it.toUiModel() },
    method = method,
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else Constants.ASSETS_URI_PREFIX + imageUrl,
    isFavorite = false
)