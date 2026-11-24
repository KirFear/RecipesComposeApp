package ru.ksurgachev.recipesappcompose.features.recipes.ui

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.placeholder
import ru.ksurgachev.recipe_app_compose.R
import ru.ksurgachev.recipesappcompose.core.ui.ScreenHeader
import ru.ksurgachev.recipesappcompose.features.recipes.presentation.RecipesViewModel
import ru.ksurgachev.recipesappcompose.ui.theme.Dimens

@Composable
fun RecipesScreen(
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: RecipesViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val state = uiState
    val categoryTitle: String = Uri.decode(state.categoryTitle)
    val categoryImageUrl: String = Uri.decode(state.categoryImageUrl)

    val context = LocalContext.current
    val screenHeaderImage = remember(categoryImageUrl, context) {
        ImageRequest.Builder(context)
            .data(categoryImageUrl)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_error)
            .build()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        ScreenHeader(
            imagePainter = rememberAsyncImagePainter(model = screenHeaderImage),
            contentDescription = categoryTitle,
            title = categoryTitle,
        )

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.error,
                        modifier = Modifier.padding(horizontal = Dimens.paddingMain),
                        textAlign = TextAlign.Center
                    )
                }
            }

            state.recipes.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Recipes list is empty",
                        modifier = Modifier.padding(horizontal = Dimens.paddingMain),
                        textAlign = TextAlign.Center
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(Dimens.paddingMain),
                    verticalArrangement = Arrangement.spacedBy(Dimens.paddingMain),
                ) {
                    items(state.recipes, key = { it.id }) { item ->
                        RecipeItem(
                            recipe = item,
                            onRecipeClick = onRecipeClick,
                        )
                    }
                }
            }
        }
    }
}