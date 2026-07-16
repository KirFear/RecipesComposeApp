package ru.ksurgachev.recipesappcompose.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import ru.ksurgachev.recipe_app_compose.R
import ru.ksurgachev.recipesappcompose.ui.theme.Dimens

@Composable
fun ScreenHeader(
    imagePainter: Painter,
    contentDescription: String,
    title: String,
    showFavoriteButton: Boolean = false,
    isFavorite: Boolean = false,
    onFavoriteToggle: () -> Unit = {},
    showShareButton: Boolean = false,
    onShareClick: () -> Unit = {}
) {
    Box(
        Modifier.height(Dimens.headerHeight)
    ) {
        Image(
            painter = imagePainter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(Dimens.paddingMain),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)
        ) {
            if (showFavoriteButton) {
                Crossfade(
                    targetState = isFavorite,
                    animationSpec = tween(durationMillis = 300),
                    label = "favorite_animation"
                ) { isCurrentlyFavorite ->
                    val heartIcon = rememberVectorPainter(
                        image = ImageVector.vectorResource(
                            id = if (isCurrentlyFavorite) R.drawable.ic_heart_favorite
                            else R.drawable.ic_heart
                        )
                    )
                    Icon(
                        painter = heartIcon,
                        contentDescription = if (isCurrentlyFavorite) "Убрать из избранного"
                        else "Добавить в избранное",
                        modifier = Modifier
                            .size(Dimens.iconLarge)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onFavoriteToggle() },
                        tint = Color.Unspecified
                    )
                }
            }

            if (showShareButton) {
                Icon(
                    painter = painterResource(R.drawable.ic_share_arrow),
                    contentDescription = "Поделиться",
                    modifier = Modifier
                        .size(Dimens.iconLarge)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(
                                bounded = false,
                                radius = Dimens.iconLarge / 2,
                            )
                        ) { onShareClick() },
                    tint = Color.White
                )
            }
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(Dimens.paddingMain),
            shape = RoundedCornerShape(Dimens.buttonCornerRadius)
        ) {
            Text(
                text = title.uppercase(),
                modifier = Modifier
                    .padding(Dimens.paddingMedium),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}