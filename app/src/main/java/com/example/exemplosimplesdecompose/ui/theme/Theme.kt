package com.example.exemplosimplesdecompose.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

enum class ContrastLevel {
    Default,
    Medium,
    High
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    contrastLevel: ContrastLevel = ContrastLevel.Default,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> highContrastDarkColorScheme
            ContrastLevel.Medium -> mediumContrastDarkColorScheme
            ContrastLevel.Default -> darkScheme
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> highContrastLightColorScheme
            ContrastLevel.Medium -> mediumContrastLightColorScheme
            ContrastLevel.Default -> lightScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

@Composable
fun getThemeColors(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): androidx.compose.material3.ColorScheme {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> highContrastDarkColorScheme
            ContrastLevel.Medium -> mediumContrastDarkColorScheme
            ContrastLevel.Default -> darkScheme
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> highContrastLightColorScheme
            ContrastLevel.Medium -> mediumContrastLightColorScheme
            ContrastLevel.Default -> lightScheme
        }
    }
}

@Composable
fun getCurrentColors(): androidx.compose.material3.ColorScheme {
    return MaterialTheme.colorScheme
}

@Composable
fun getAppBackground(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> backgroundDarkHighContrast
            ContrastLevel.Medium -> backgroundDarkMediumContrast
            ContrastLevel.Default -> backgroundDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> backgroundLightHighContrast
            ContrastLevel.Medium -> backgroundLightMediumContrast
            ContrastLevel.Default -> backgroundLight
        }
    }
}

@Composable
fun getAppPrimary(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> primaryDarkHighContrast
            ContrastLevel.Medium -> primaryDarkMediumContrast
            ContrastLevel.Default -> primaryDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> primaryLightHighContrast
            ContrastLevel.Medium -> primaryLightMediumContrast
            ContrastLevel.Default -> primaryLight
        }
    }
}

@Composable
fun getAppOnPrimary(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> onPrimaryDarkHighContrast
            ContrastLevel.Medium -> onPrimaryDarkMediumContrast
            ContrastLevel.Default -> onPrimaryDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> onPrimaryLightHighContrast
            ContrastLevel.Medium -> onPrimaryLightMediumContrast
            ContrastLevel.Default -> onPrimaryLight
        }
    }
}

@Composable
fun getAppSecondary(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> secondaryDarkHighContrast
            ContrastLevel.Medium -> secondaryDarkMediumContrast
            ContrastLevel.Default -> secondaryDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> secondaryLightHighContrast
            ContrastLevel.Medium -> secondaryLightMediumContrast
            ContrastLevel.Default -> secondaryLight
        }
    }
}

@Composable
fun getAppOnSecondary(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> onSecondaryDarkHighContrast
            ContrastLevel.Medium -> onSecondaryDarkMediumContrast
            ContrastLevel.Default -> onSecondaryDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> onSecondaryLightHighContrast
            ContrastLevel.Medium -> onSecondaryLightMediumContrast
            ContrastLevel.Default -> onSecondaryLight
        }
    }
}

@Composable
fun getAppError(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> errorDarkHighContrast
            ContrastLevel.Medium -> errorDarkMediumContrast
            ContrastLevel.Default -> errorDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> errorLightHighContrast
            ContrastLevel.Medium -> errorLightMediumContrast
            ContrastLevel.Default -> errorLight
        }
    }
}

@Composable
fun getAppOnSurface(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> onSurfaceDarkHighContrast
            ContrastLevel.Medium -> onSurfaceDarkMediumContrast
            ContrastLevel.Default -> onSurfaceDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> onSurfaceLightHighContrast
            ContrastLevel.Medium -> onSurfaceLightMediumContrast
            ContrastLevel.Default -> onSurfaceLight
        }
    }
}

@Composable
fun getAppOnSurfaceVariant(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> onSurfaceVariantDarkHighContrast
            ContrastLevel.Medium -> onSurfaceVariantDarkMediumContrast
            ContrastLevel.Default -> onSurfaceVariantDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> onSurfaceVariantLightHighContrast
            ContrastLevel.Medium -> onSurfaceVariantLightMediumContrast
            ContrastLevel.Default -> onSurfaceVariantLight
        }
    }
}

@Composable
fun getAppOutline(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> outlineDarkHighContrast
            ContrastLevel.Medium -> outlineDarkMediumContrast
            ContrastLevel.Default -> outlineDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> outlineLightHighContrast
            ContrastLevel.Medium -> outlineLightMediumContrast
            ContrastLevel.Default -> outlineLight
        }
    }
}

@Composable
fun getAppPrimaryContainer(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> primaryContainerDarkHighContrast
            ContrastLevel.Medium -> primaryContainerDarkMediumContrast
            ContrastLevel.Default -> primaryContainerDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> primaryContainerLightHighContrast
            ContrastLevel.Medium -> primaryContainerLightMediumContrast
            ContrastLevel.Default -> primaryContainerLight
        }
    }
}

@Composable
fun getAppOnPrimaryContainer(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> onPrimaryContainerDarkHighContrast
            ContrastLevel.Medium -> onPrimaryContainerDarkMediumContrast
            ContrastLevel.Default -> onPrimaryContainerDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> onPrimaryContainerLightHighContrast
            ContrastLevel.Medium -> onPrimaryContainerLightMediumContrast
            ContrastLevel.Default -> onPrimaryContainerLight
        }
    }
}

@Composable
fun getAppSecondaryContainer(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> secondaryContainerDarkHighContrast
            ContrastLevel.Medium -> secondaryContainerDarkMediumContrast
            ContrastLevel.Default -> secondaryContainerDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> secondaryContainerLightHighContrast
            ContrastLevel.Medium -> secondaryContainerLightMediumContrast
            ContrastLevel.Default -> secondaryContainerLight
        }
    }
}

@Composable
fun getAppOnSecondaryContainer(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> onSecondaryContainerDarkHighContrast
            ContrastLevel.Medium -> onSecondaryContainerDarkMediumContrast
            ContrastLevel.Default -> onSecondaryContainerDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> onSecondaryContainerLightHighContrast
            ContrastLevel.Medium -> onSecondaryContainerLightMediumContrast
            ContrastLevel.Default -> onSecondaryContainerLight
        }
    }
}

@Composable
fun getAppSurface(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> surfaceDarkHighContrast
            ContrastLevel.Medium -> surfaceDarkMediumContrast
            ContrastLevel.Default -> surfaceDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> surfaceLightHighContrast
            ContrastLevel.Medium -> surfaceLightMediumContrast
            ContrastLevel.Default -> surfaceLight
        }
    }
}

@Composable
fun getAppSurfaceContainer(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> surfaceContainerDarkHighContrast
            ContrastLevel.Medium -> surfaceContainerDarkMediumContrast
            ContrastLevel.Default -> surfaceContainerDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> surfaceContainerLightHighContrast
            ContrastLevel.Medium -> surfaceContainerLightMediumContrast
            ContrastLevel.Default -> surfaceContainerLight
        }
    }
}

@Composable
fun getAppSurfaceVariant(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> surfaceVariantDarkHighContrast
            ContrastLevel.Medium -> surfaceVariantDarkMediumContrast
            ContrastLevel.Default -> surfaceVariantDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> surfaceVariantLightHighContrast
            ContrastLevel.Medium -> surfaceVariantLightMediumContrast
            ContrastLevel.Default -> surfaceVariantLight
        }
    }
}

@Composable
fun getAppErrorContainer(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> errorContainerDarkHighContrast
            ContrastLevel.Medium -> errorContainerDarkMediumContrast
            ContrastLevel.Default -> errorContainerDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> errorContainerLightHighContrast
            ContrastLevel.Medium -> errorContainerLightMediumContrast
            ContrastLevel.Default -> errorContainerLight
        }
    }
}

@Composable
fun getAppOnErrorContainer(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> onErrorContainerDarkHighContrast
            ContrastLevel.Medium -> onErrorContainerDarkMediumContrast
            ContrastLevel.Default -> onErrorContainerDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> onErrorContainerLightHighContrast
            ContrastLevel.Medium -> onErrorContainerLightMediumContrast
            ContrastLevel.Default -> onErrorContainerLight
        }
    }
}

@Composable
fun getAppOnError(
    darkTheme: Boolean = isSystemInDarkTheme(),
    contrastLevel: ContrastLevel = ContrastLevel.Default
): Color {
    return when {
        darkTheme -> when (contrastLevel) {
            ContrastLevel.High -> onErrorDarkHighContrast
            ContrastLevel.Medium -> onErrorDarkMediumContrast
            ContrastLevel.Default -> onErrorDark
        }
        else -> when (contrastLevel) {
            ContrastLevel.High -> onErrorLightHighContrast
            ContrastLevel.Medium -> onErrorLightMediumContrast
            ContrastLevel.Default -> onErrorLight
        }
    }
}

