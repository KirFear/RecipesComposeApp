package ru.ksurgachev.recipesappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import ru.ksurgachev.recipesappcompose.ui.theme.Dimens
import ru.ksurgachev.recipesappcompose.ui.theme.RecipesAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
                                .padding(Dimens.paddingSmall)
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
    }
}