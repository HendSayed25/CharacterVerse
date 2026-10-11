package com.characterverse.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SciFiDarkColorScheme = darkColorScheme(
    primary = PortalGreen,
    onPrimary = Color(0xFF003817),
    primaryContainer = Color(0xFF005225),
    onPrimaryContainer = PortalGreenLight,

    secondary = PortalCyan,
    onSecondary = Color(0xFF00363D),
    secondaryContainer = Color(0xFF004F59),
    onSecondaryContainer = Color(0xFF97F0FF),

    tertiary = SciFiAccent,
    onTertiary = Color.White,

    background = DarkBackground,
    onBackground = TextPrimary,

    surface = DarkSurface,
    onSurface = TextPrimary,

    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextSecondary,

    outline = DarkCardBorder,
    outlineVariant = Color(0xFF1F2D40)
)

@Composable
fun CharacterVerseTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SciFiDarkColorScheme,
        typography = Typography,
        content = content
    )
}
