package com.example.nba.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1e2224),
    secondary = Color(0xFFfa8100),
    tertiary = Color(0xFFff6c37),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    background = Color(0xFF2e3336),
    surfaceTint = Color(0x1AFA8100),
    inversePrimary = Color(0xCCFFFFFF)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFfdfdfd),
    secondary = Color(0xFFfa8100),
    tertiary = Color(0xFFff6c37),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    background = Color(0xFFfdfdfd),
    surfaceTint = Color(0x1AFA8100),
    inversePrimary = Color(0xCCFFFFFF)

    /* Other default colors to override
    surface = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
/**
 * Main app theme
 * @param darkTheme whether app is in dark mode or not
 * @param dynamicColor Dynamic color is available on Android 12+
 * @param content which will be contained in this theme
 */
fun NBATheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}