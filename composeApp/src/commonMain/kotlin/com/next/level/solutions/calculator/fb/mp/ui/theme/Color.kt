package com.next.level.solutions.calculator.fb.mp.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val DarkBackground = Color(0xFF3D6063)
val DarkOnBackground = Color(0xFFFFFFFF)
val DarkPrimary = Color(0xFF3A898E)
val DarkOnPrimary = DarkOnBackground
val DarkSecondary = DarkOnBackground
val DarkOnSecondary = Color(0xFF353535)
val DarkSecondaryContainer = Color(0xFFC0C0C0)
val DarkOnSecondaryContainer = DarkOnBackground
val DarkSurface = Color(0xFF6F9395)
val DarkSurfaceContainer = DarkOnBackground
val DarkError = Color(0xFFE53935)
val DarkScrim = Color.Black.copy(alpha = 0.7f)

val DarkColorScheme = darkColorScheme(
    background = DarkBackground,
    onBackground = DarkOnBackground,

    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,

    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,

    surface = DarkSurface,
    surfaceContainer = DarkSurfaceContainer,

    error = DarkError,
    scrim = DarkScrim,

    outlineVariant = DarkOnBackground.copy(alpha = 0.1f),
)

val LightBackground = Color(0xFFF0EFEF)
val LightOnBackground = Color.Black
val LightPrimary = DarkPrimary
val LightOnPrimary = DarkOnPrimary
val LightSecondary = DarkSecondary
val LightOnSecondary = DarkOnSecondary
val LightSecondaryContainer = Color(0xFFCBCBCB)
val LightOnSecondaryContainer = LightOnBackground
val LightError = DarkError
val LightScrim = DarkScrim
val LightSurface = DarkOnBackground
val LightSurfaceContainer = DarkSurface

val LightColorScheme = lightColorScheme(
    background = LightBackground,
    onBackground = LightOnBackground,

    primary = LightPrimary,
    onPrimary = LightOnPrimary,

    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    secondaryContainer = LightSecondaryContainer,
    onSecondaryContainer = LightOnSecondaryContainer,

    surface = LightSurface,
    surfaceContainer = LightSurfaceContainer,

    error = LightError,
    scrim = LightScrim,

    outlineVariant = LightOnBackground.copy(alpha = 0.1f),
)