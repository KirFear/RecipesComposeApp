package ru.ksurgachev.recipesappcompose.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.placeholder
import ru.ksurgachev.recipe_app_compose.R
import ru.ksurgachev.recipesappcompose.ui.components.ScreenHeader
import ru.ksurgachev.recipesappcompose.ui.recipes.model.IngredientUiModel
import ru.ksurgachev.recipesappcompose.ui.recipes.model.RecipeUiModel
import ru.ksurgachev.recipesappcompose.ui.theme.Dimens
import ru.ksurgachev.recipesappcompose.ui.utils.shareRecipe
import kotlin.math.roundToInt

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var isFavorite by rememberSaveable { mutableStateOf(false) }
    val servingsNumber = 4
    var currentPortions by rememberSaveable { mutableIntStateOf(servingsNumber) }
    val scaledIngredients = remember(recipe.ingredients, currentPortions) {
        val multiplier = currentPortions.toDouble() / servingsNumber

        recipe.ingredients.map { ingredient ->
            ingredient.quantity.toDoubleOrNull()?.let { quantity ->
                ingredient.copy(
                    quantity = getNewQuantity(quantity, multiplier)
                )
            } ?: ingredient
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        ScreenHeader(
            imagePainter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(recipe.imageUrl)
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_error)
                    .build()
            ),
            contentDescription = recipe.title,
            title = recipe.title,
            showFavoriteButton = true,
            isFavorite = isFavorite,
            onFavoriteToggle = { isFavorite = !isFavorite },
            showShareButton = true,
            onShareClick = { shareRecipe(context, recipe.id, recipe.title) }
        )

        Column(
            modifier = Modifier.padding(Dimens.paddingMain),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall),
        ) {
            Text(
                text = "Ингредиенты".uppercase(),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.displayLarge
            )

            Text(
                text = "Порции: $currentPortions",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )

            PortionsSlider(
                currentPortions = currentPortions,
                onPortionsChange = { newValue ->
                    currentPortions = newValue
                }
            )
        }

        Column(
            modifier = Modifier
                .padding(Dimens.paddingMain)
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(Dimens.buttonCornerRadius)
                )
                .padding(Dimens.paddingMain),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
        ) {
            scaledIngredients.forEachIndexed { index, ingredient ->
                IngredientItem(ingredient)
                if (index < scaledIngredients.lastIndex) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                }
            }
        }

        Text(
            text = "Способ приготовления".uppercase(),
            modifier = Modifier
                .padding(Dimens.paddingMain),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displayLarge
        )

        Column(
            modifier = Modifier
                .padding(Dimens.paddingMain)
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(Dimens.buttonCornerRadius)
                )
                .padding(Dimens.paddingMain),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
        ) {
            recipe.method.forEachIndexed { index, method ->
                Text(
                    text = "${index + 1}. $method",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (index < recipe.method.lastIndex) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                }
            }
        }
    }
}

@Composable
fun IngredientItem(
    ingredient: IngredientUiModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = ingredient.name.uppercase(),
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "${ingredient.quantity.uppercase()} ${ingredient.unitOfMeasure.uppercase()}",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortionsSlider(
    currentPortions: Int,
    onPortionsChange: (Int) -> Unit
) {
    Slider(
        value = currentPortions.toFloat(),
        onValueChange = { onPortionsChange(it.roundToInt()) },
        valueRange = 1f..12f,
        track = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.sliderTrackHeight)
                    .clip(RoundedCornerShape(Dimens.sliderTrackRadius))
                    .background(color = MaterialTheme.colorScheme.tertiaryContainer)
            )
        },
        thumb = {
            Box(
                modifier = Modifier
                    .height(Dimens.sliderThumbHeight)
                    .width(Dimens.sliderThumbWidth)
                    .clip(RoundedCornerShape(Dimens.sliderThumbRadius))
                    .background(color = MaterialTheme.colorScheme.tertiary)
            )
        }
    )
}

fun getNewQuantity(quantity: Double, multiplier: Double): String {
    val raw = quantity * multiplier
    val newQuantity = if (raw < 0.25) {
        raw  // точное значение
    } else {
        (raw * 4).roundToInt() / 4.0  // округление до 0.25
    }.toBigDecimal().stripTrailingZeros().toPlainString()

    return newQuantity
}