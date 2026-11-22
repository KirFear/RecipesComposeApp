package ru.ksurgachev.recipesappcompose.features.categories.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.ksurgachev.recipe_app_compose.R
import ru.ksurgachev.recipesappcompose.core.ui.ScreenHeader
import ru.ksurgachev.recipesappcompose.features.categories.presentation.CategoriesViewModel
import ru.ksurgachev.recipesappcompose.ui.theme.Dimens

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (Int, String, String) -> Unit,
) {
    val viewModel: CategoriesViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val state = uiState

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        ScreenHeader(
            imagePainter = (painterResource(R.drawable.img_categories)),
            contentDescription = "Категории",
            title = "Категории",
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

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(Dimens.paddingMain),
                    verticalArrangement = Arrangement.spacedBy(Dimens.paddingMain),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.paddingMain)
                ) {
                    items(state.categories, { it.id }) { item ->
                        CategoryItem(
                            category = item,
                            onClick = onCategoryClick,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}
