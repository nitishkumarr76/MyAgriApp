package com.example.myagri.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = GreenPrimary ,
    onSurface = GreenLight,
    background = Background,
    surface = White,
    onPrimary = White,
    onBackground = TextDark
)

@Composable
fun MyAgriTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}