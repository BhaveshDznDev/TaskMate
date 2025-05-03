package com.example.taskmanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryVariant,
    onPrimaryContainer = OnPrimaryLight,
    secondary = SecondaryColor,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryColor,
    onSecondaryContainer = OnSecondaryLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight
)

private val DarkColors = darkColorScheme(
    primary = PrimaryColor,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryVariant,
    onPrimaryContainer = OnPrimaryDark,
    secondary = SecondaryColor,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryColor,
    onSecondaryContainer = OnSecondaryDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark
)

@Composable
fun TaskManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}
