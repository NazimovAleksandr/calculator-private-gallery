package com.next.level.solutions.calculator.fb.mp.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
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

val DarkColorScheme = darkColors(
    background = DarkBackground,
    onBackground = DarkOnBackground,

    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,

    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    primaryVariant = DarkSecondaryContainer, // secondaryContainer
    secondaryVariant = DarkOnSecondaryContainer, // onSecondaryContainer

    error = DarkError,

    surface = DarkSurface,
    onSurface = DarkTertiary, // tertiary

//    onError: Color = Color.Black
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

val LightColorScheme = lightColors(
    background = LightBackground,
    onBackground = LightOnBackground,

    primary = LightPrimary,
    onPrimary = LightOnPrimary,

    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    primaryVariant = LightSecondaryContainer, // secondaryContainer
    secondaryVariant = LightOnSecondaryContainer, // onSecondaryContainer

    error = LightError,

    surface = LightSurface,
    onSurface = LightTertiary, // tertiary
)
