package ru.ksurgachev.recipesappcompose.features.recipes.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ksurgachev.recipesappcompose.core.utils.Constants
import ru.ksurgachev.recipesappcompose.data.repository.getRecipesByCategoryId
import ru.ksurgachev.recipesappcompose.features.recipes.presentation.model.RecipesUiState
import ru.ksurgachev.recipesappcompose.features.recipes.presentation.model.toUiModel

class RecipesViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    private val categoryId: Int? = savedStateHandle.get<Int>(Constants.KEY_CATEGORY_ID)
    private val categoryTitle: String = savedStateHandle.get<String>(Constants.KEY_CATEGORY_TITLE) ?: ""
    private val categoryImageUrl: String = savedStateHandle.get<String>(Constants.KEY_CATEGORY_IMAGE_URL) ?: ""

    init {
        if (categoryId == null) {
            _uiState.update { it.copy(error = "Category not specified") }
        } else {
            loadRecipes(categoryId)
        }
    }

    fun loadRecipes(categoryId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val recipes = getRecipesByCategoryId(categoryId).map { it.toUiModel() }

                _uiState.update {
                    it.copy(
                        recipes = recipes,
                        categoryTitle = categoryTitle,
                        categoryImageUrl = categoryImageUrl,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "Unknown error")
                }
            }
        }
    }
}
