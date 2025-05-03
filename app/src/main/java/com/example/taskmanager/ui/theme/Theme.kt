package com.example.taskmanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary            = Purple500,
    onPrimary          = White,
    primaryContainer   = Purple100,
    onPrimaryContainer = Black,

    secondary            = Blue600,
    onSecondary          = White,
    secondaryContainer   = Blue100,
    onSecondaryContainer = Black,

    tertiary            = Orange600,
    onTertiary          = White,
    tertiaryContainer   = Orange100,
    onTertiaryContainer = Black,

    background     = White,
    onBackground   = Gray900,
    surface        = White,
    onSurface      = Gray900,
)

private val DarkColors = darkColorScheme(
    primary            = Purple200,
    onPrimary          = Black,
    primaryContainer   = Purple700,
    onPrimaryContainer = White,

    secondary            = Blue200,
    onSecondary          = Black,
    secondaryContainer   = Blue600,
    onSecondaryContainer = White,

    tertiary            = Orange200,
    onTertiary          = Black,
    tertiaryContainer   = Orange600,
    onTertiaryContainer = White,

    background     = Gray900,
    onBackground   = White,
    surface        = Gray800,
    onSurface      = White,
)

@Composable
fun TaskManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography  = Typography,
        content     = content
    )
}
