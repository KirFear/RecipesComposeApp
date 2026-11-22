package ru.ksurgachev.recipesappcompose.features.categories.presentation.model

data class CategoriesUiState(
    var categories: List<CategoryUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
