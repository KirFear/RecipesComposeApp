package ru.ksurgachev.recipesappcompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColorDark,
    onPrimary = Color(0xFF2D1B4E),

    error = AccentColorDark,
    onError = Color(0xFF5C1F1F),

    tertiary = AccentBlueDark,
    onTertiary = Color(0xFF1A3A5C),

    tertiaryContainer = SliderTrackColorDark,
    onTertiaryContainer = TextPrimaryColorDark,

    background = BackgroundColorDark,
    onBackground = TextPrimaryColorDark,

    surface = SurfaceColorDark,
    onSurface = TextPrimaryColorDark,

    surfaceVariant = SurfaceVariantColorDark,
    onSurfaceVariant = TextSecondaryColorDark,

    outline = DividerColorDark,
    outlineVariant = Color(0xFF3C3C3C)
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = Color.White,

    error = AccentColor,
    onError = Color.White,

    tertiary = AccentBlue,
    onTertiary = Color.White,

    tertiaryContainer = SliderTrackColor,
    onTertiaryContainer = TextPrimaryColor,

    background = BackgroundColor,
    onBackground = TextPrimaryColor,

    surface = SurfaceColor,
    onSurface = TextPrimaryColor,

    surfaceVariant = SurfaceVariantColor,
    onSurfaceVariant = TextSecondaryColor,

    outline = DividerColor,
    outlineVariant = Color(0xFFE0E0E0)
)

@Composable
fun RecipesAppComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = recipesAppTypography,
        content = content
    )
}