package ru.ksurgachev.recipesappcompose.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.ksurgachev.recipesappcompose.ui.theme.Dimens

@Composable
fun BottomNavigation(
    onCategoriesClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .padding(horizontal = Dimens.paddingMain),
        horizontalArrangement = Arrangement.spacedBy(Dimens.paddingMain)
    ) {
        Button(
            onClick = onCategoriesClick,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(Dimens.buttonCornerRadius),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            ),
        ) {
            Text(
                "КАТЕГОРИИ",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Button(
            onClick = onFavoriteClick,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(Dimens.buttonCornerRadius),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ),
        ) {
            Text(
                "ИЗБРАННОЕ",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    BottomNavigation({}, {})
}