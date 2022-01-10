package com.swein.shjetpackcompose.basic.customthemeexample.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*

private val CustomDarkColors = CustomColors(
    customBackgroundColor = Color222222,
    customButtonBackgroundColor = Color03DAC5,
    customButtonTextColor = Color222222,
    customTextColor = ColorCCCCCC,
)

private val CustomLightColors = CustomColors(
    customBackgroundColor = ColorCCCCCC,
    customButtonBackgroundColor = Color3700B3,
    customButtonTextColor = ColorCCCCCC,
    customTextColor = Color222222,
)

private val LocalColorsProvider = staticCompositionLocalOf {
    CustomLightColors
}

@Composable
private fun CustomLocalProvider(colors: CustomColors, content: @Composable () -> Unit) {
    val colorPalette = remember {
        colors.copy()
    }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalColorsProvider provides colorPalette, content = content)
}

private val CustomTheme.colors: Pair<Colors, CustomColors>
    get() = when (this) {
        CustomTheme.DARK -> darkColors() to CustomDarkColors
        CustomTheme.LIGHT -> lightColors() to CustomLightColors
    }


object CustomThemeManager {

    val colors: CustomColors
        @Composable
        get() = LocalColorsProvider.current

    var customTheme by mutableStateOf(CustomTheme.LIGHT)

    fun isSystemInDarkTheme(): Boolean {
        return customTheme == CustomTheme.DARK
    }
}


@Composable
fun CustomJetpackComposeTheme(
    customTheme: CustomTheme = CustomThemeManager.customTheme,
    content: @Composable () -> Unit
) {

    val (colorPalette, lcColor) = customTheme.colors

    CustomLocalProvider(colors = lcColor) {
        MaterialTheme(
            colors = colorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}