package com.scaffold.app.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// 品牌色：改这里即可全局换肤（或在 Theme.kt 中开启 dynamicColor 动态取色）
val BrandPrimary = Color(0xFF1565C0)
val BrandOnPrimary = Color(0xFFFFFFFF)
val BrandPrimaryContainer = Color(0xFFD6E3FF)
val BrandSecondary = Color(0xFF0288D1)
val BrandTertiary = Color(0xFF00695C)

val LightColors = lightColorScheme(
    primary = BrandPrimary,
    onPrimary = BrandOnPrimary,
    primaryContainer = BrandPrimaryContainer,
    secondary = BrandSecondary,
    tertiary = BrandTertiary,
)

val DarkColors = darkColorScheme(
    primary = Color(0xFF90CAF9),
    onPrimary = Color(0xFF003258),
    primaryContainer = Color(0xFF00497D),
    secondary = Color(0xFF4DD0E1),
    tertiary = Color(0xFF4DB6AC),
)
