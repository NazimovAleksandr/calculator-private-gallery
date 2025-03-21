package com.next.level.solutions.calculator.fb.mp.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val DarkBackground = Color(0xFF3D6063)
val DarkOnBackground = Color(0xFFFFFFFF)
//val DarkPrimary = Color(0xFF3C6E71)
val DarkPrimary = Color(0xFF3A898E)
//val DarkTertiary = Color(0xFF44ACAC)
val DarkOnPrimary = Color(0xFFFAFAFA)
val DarkSecondary = DarkOnPrimary
val DarkOnSecondary = Color(0xFF353535)

val DarkSecondaryContainer = Color(0xFFC0C0C0)
val DarkOnSecondaryContainer = Color(0xFFFFFFFF)

val DarkSurface = Color(0xFF6F9395)
val DarkOnSurface = DarkOnPrimary

val DarkError = Color(0xFFE53935)
val DarkScrim = Color.Black.copy(alpha = 0.7f)
val DarkSurfaceContainer = Color(0xFF333333)

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

    surface = DarkSurface,
    onSurface = DarkOnSurface,

    error = DarkError,

    scrim = DarkScrim,
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

    error = LightError,

    scrim = LightScrim,
    surface = LightSurface,
    surfaceContainer = LightSurfaceContainer,

    outlineVariant = LightOnBackground.copy(alpha = 0.1f),
)