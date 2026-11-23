package ru.ksurgachev.recipesappcompose.features.recipes.presentation.model

data class RecipesUiState(
    val recipes: List<RecipeUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
