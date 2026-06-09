package ru.ksurgachev.recipesappcompose.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.ksurgachev.recipesappcompose.Constants
import ru.ksurgachev.recipesappcompose.data.repository.getCategories
import ru.ksurgachev.recipesappcompose.ui.categories.model.toUiModel
import ru.ksurgachev.recipesappcompose.ui.components.ScreenHeader
import ru.ksurgachev.recipesappcompose.ui.theme.Dimens

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (Int, String, String) -> Unit
) {
    val categoryItems = getCategories().map { it.toUiModel() }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        ScreenHeader(
            imageUrl = Constants.ASSETS_URI_PREFIX + "bcg_categories.png",
            contentDescription = "Категории",
            title = "Категории"
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(Dimens.paddingMain),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingMain),
            horizontalArrangement = Arrangement.spacedBy(Dimens.paddingMain)
        ) {
            items(categoryItems, { it.id }) { item ->
                CategoryItem(
                    category = item,
                    onClick = onCategoryClick,
                    modifier = Modifier
                )
            }
        }
    }
}
