package ru.ksurgachev.recipesappcompose.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ru.ksurgachev.recipe_app_compose.R
import ru.ksurgachev.recipesappcompose.data.repository.getCategories
import ru.ksurgachev.recipesappcompose.ui.categories.model.toUiModel
import ru.ksurgachev.recipesappcompose.ui.components.ScreenHeader
import ru.ksurgachev.recipesappcompose.ui.theme.Dimens

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier, onCategoryClick: (Int) -> Unit) {
    val categoryItems = getCategories().map { it.toUiModel() }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        ScreenHeader(
            imagePainter = painterResource(id = R.drawable.img_categories),
            contentDescription = "Категории",
            title = "КАТЕГОРИИ"
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(Dimens.paddingMain),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingMain),
            horizontalArrangement = Arrangement.spacedBy(Dimens.paddingMain)
        ) {
            items(categoryItems) { item ->
                CategoryItem(
                    item,
                    onCategoryClick,
                    Modifier
                )
            }
        }
    }
}
