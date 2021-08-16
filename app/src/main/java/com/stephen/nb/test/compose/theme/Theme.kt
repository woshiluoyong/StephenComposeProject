package com.stephen.nb.test.compose.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.stephen.nb.test.compose.MyApplication

private val LightColorPalette = lightColors(
    background = BackgroundColorLight,
    primary = PrimaryColorLight,
    primaryVariant = PrimaryVariantColorLight,
    surface = SurfaceColorLight,
    secondary = DivideColorLight,
)

private val DarkColorPalette = darkColors(
    background = BackgroundColorDark,
    primary = PrimaryColorDark,
    primaryVariant = PrimaryVariantColorDark,
    surface = SurfaceColorDark,
    secondary = DivideColorDark,
)

private val PinkColorPalette = lightColors(
    background = BackgroundColorPink,
    primary = PrimaryColorPink,
    primaryVariant = PrimaryVariantColorPink,
    surface = SurfaceColorPink,
    secondary = DivideColorPink,
)

@Composable
fun MyAppTheme(appTheme: AppTheme = MyApplication.currentTheme, content: @Composable () -> Unit) {
    val colors = when (appTheme) {
        AppTheme.Light -> LightColorPalette
        AppTheme.Dark -> DarkColorPalette
        AppTheme.Pink -> PinkColorPalette
    }
    val typography = if (appTheme.isDarkTheme()) {
        DarkTypography
    } else {
        LightTypography
    }
    MaterialTheme(colors = colors, typography = typography, shapes = AppShapes, content = content)
}