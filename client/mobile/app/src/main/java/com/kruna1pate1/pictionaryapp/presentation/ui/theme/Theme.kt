package com.kruna1pate1.pictionaryapp.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = DarkGreen,
    primaryVariant = LightGreen,
    secondary = Orange,
    background = Black,
    surface = DarkGray,
    error = Red,
    onPrimary = Color.White,
    onSurface = White,
    onBackground = White
)

@Composable
fun PictionaryTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
