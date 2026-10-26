package com.udistrital.appcivica.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val RedBlackColorScheme = darkColorScheme(
    primary = VibrantRedPrimary,
    onPrimary = VibrantRedOnPrimary,
    primaryContainer = RedContainer,
    onPrimaryContainer = RedOnContainer,
    secondary = TextRoseSecondary,
    onSecondary = Color.Black,
    background = RedGradientStart,
    onBackground = TextWhitePrimary,
    surface = DarkSurfaceRed,
    onSurface = TextWhitePrimary,
    surfaceVariant = DarkSurfaceVariantRed,
    onSurfaceVariant = TextWhitePrimary,
    outline = TextRoseSecondary
)

@Composable
fun AppCivicaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> RedBlackColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
