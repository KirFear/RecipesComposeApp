package ru.ksurgachev.recipesappcompose.data.model

data class RecipeDto(
    val id: Long,
    val title: String,
    val ingredients: List<IngredientDto>,
    val method: List<String>,
    val imageUrl: String
)