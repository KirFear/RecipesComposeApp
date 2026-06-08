package ru.ksurgachev.recipesappcompose.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import ru.ksurgachev.recipe_app_compose.R
import ru.ksurgachev.recipesappcompose.ui.theme.Dimens

@Composable
fun ScreenHeader(
    imageUrl: String,
    contentDescription: String,
    title: String
) {
    Box(
        Modifier.height(Dimens.headerHeight)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.img_placeholder),
            error = painterResource(id = R.drawable.img_error)
        )

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