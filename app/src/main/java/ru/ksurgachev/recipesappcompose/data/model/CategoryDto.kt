package ru.ksurgachev.recipesappcompose.data.model

data class CategoryDto(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String
)