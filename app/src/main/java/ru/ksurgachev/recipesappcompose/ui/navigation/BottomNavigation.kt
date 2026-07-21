package ru.ksurgachev.recipesappcompose.ui.navigation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.ksurgachev.recipe_app_compose.R
import ru.ksurgachev.recipesappcompose.ui.theme.Dimens

@Composable
fun BottomNavigation(
    onCategoriesClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    favoriteCount: Int
) {
    Row(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .padding(horizontal = Dimens.paddingMain),
        horizontalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)
    ) {
        Button(
            onClick = onCategoriesClick,
            modifier = Modifier
                .height(Dimens.buttonHeight)
                .weight(1f),
            shape = RoundedCornerShape(Dimens.buttonCornerRadius),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            ),
        ) {
            Text(
                "КАТЕГОРИИ",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Button(
            onClick = onFavoriteClick,
            modifier = Modifier
                .height(Dimens.buttonHeight)
                .weight(1f),
            shape = RoundedCornerShape(Dimens.buttonCornerRadius),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimens.paddingMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "ИЗБРАННОЕ",
                    style = MaterialTheme.typography.titleMedium
                )

                Box(
                    modifier = Modifier.requiredSize(Dimens.iconMedium * 1.5f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_heart),
                        contentDescription = "Избранное",
                        modifier = Modifier.size(Dimens.iconMedium),
                        tint = MaterialTheme.colorScheme.onError
                    )

                    if (favoriteCount > 0) {
                        Badge(
                            modifier = Modifier.align(Alignment.TopEnd),
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                        ) {
                            Text(favoriteCount.toString())
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    BottomNavigation(
        {},
        {},
        15
    )
}