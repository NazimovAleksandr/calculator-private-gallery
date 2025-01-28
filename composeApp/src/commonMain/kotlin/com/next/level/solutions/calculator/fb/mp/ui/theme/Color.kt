package com.next.level.solutions.calculator.fb.mp.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val DarkBackground = Color(0xFF0F0F0F)
val DarkOnBackground = Color(0xFFFFFFFF)
val DarkPrimary = Color(0xFFF09200)
val DarkOnPrimary = DarkOnBackground
val DarkSecondary = Color(0xFF333333)
val DarkOnSecondary = DarkOnBackground
val DarkSecondaryContainer = Color(0xFF1D1D1D)
val DarkOnSecondaryContainer = DarkOnBackground
val DarkTertiary = Color(0xFF44ACAC)
val DarkError = Color(0xFFE53935)
val DarkScrim = Color.Black.copy(alpha = 0.7f)
val DarkSurface = Color(0xFF505050)
val DarkSurfaceContainer = DarkSecondary

val DarkColorScheme = darkColorScheme(
    background = DarkBackground,
    onBackground = DarkOnBackground,

    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
//    primaryContainer =,
//    onPrimaryContainer =,
//    inversePrimary =,

    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,

    tertiary = DarkTertiary,
    error = DarkError,

    scrim = DarkScrim,
    surface = DarkSurface,
    surfaceContainer = DarkSurfaceContainer,

    outlineVariant = DarkOnBackground.copy(alpha = 0.1f),
)

val LightBackground = Color(0xFFFFFFFF)
val LightOnBackground = Color(0xFF000000)
val LightPrimary = Color(0xFFF09200)
val LightOnPrimary = LightBackground
val LightSecondary = Color(0xFF9B9B9B)
val LightOnSecondary = LightBackground
val LightSecondaryContainer = Color(0xFFCBCBCB)
val LightOnSecondaryContainer = LightOnBackground
val LightTertiary = DarkTertiary
val LightError = DarkError
val LightScrim = DarkScrim
val LightSurface = DarkSurface
val LightSurfaceContainer = LightBackground

val LightColorScheme = lightColorScheme(
    background = LightBackground,
    onBackground = LightOnBackground,

    primary = LightPrimary,
    onPrimary = LightOnPrimary,
//    primaryContainer =,
//    onPrimaryContainer =,
//    inversePrimary =,

    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    secondaryContainer = LightSecondaryContainer,
    onSecondaryContainer = LightOnSecondaryContainer,

    tertiary = LightTertiary,
    error = LightError,

    scrim = LightScrim,
    surface = LightSurface,
    surfaceContainer = LightSurfaceContainer,

    outlineVariant = LightOnBackground.copy(alpha = 0.1f),
)