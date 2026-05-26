package ru.ksurgachev.recipesappcompose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.ksurgachev.recipesappcompose.ui.theme.Dimens
import ru.ksurgachev.recipesappcompose.ui.theme.RecipesAppComposeTheme

@Composable
fun RecipesApp(){
    RecipesAppComposeTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.cardHeight)
                        .padding(Dimens.paddingSmall),
                    colors = CardDefaults.cardColors()
                ) {
                    Text(
                        text = "Recipes App",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(Dimens.paddingMedium)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesAppPreview(){
    RecipesApp()
}