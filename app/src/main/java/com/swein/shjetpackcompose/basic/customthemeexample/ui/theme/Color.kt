package com.swein.shjetpackcompose.basic.customthemeexample.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

enum class CustomTheme {
    DARK, LIGHT
}

val Color3700B3 = Color(0xFF3700B3)
val Color03DAC5 = Color(0xFF03DAC5)
val Color222222 = Color(0xFF222222)
val ColorCCCCCC = Color(0xFFCCCCCC)

@Stable
class CustomColors(
    customBackgroundColor: Color,
    customButtonBackgroundColor: Color,
    customButtonTextColor: Color,
    customTextColor: Color
) {
    var customBackgroundColor by mutableStateOf(customBackgroundColor)
        private set

    var customButtonBackgroundColor by mutableStateOf(customButtonBackgroundColor)
        private set

    var customButtonTextColor by mutableStateOf(customButtonTextColor)
        private set

    var customTextColor by mutableStateOf(customTextColor)
        private set

    fun update(colors: CustomColors) {
        this.customBackgroundColor = colors.customBackgroundColor
        this.customButtonBackgroundColor = colors.customButtonBackgroundColor
        this.customButtonTextColor = colors.customButtonTextColor
        this.customTextColor = colors.customTextColor
    }

    fun copy() = CustomColors(customBackgroundColor, customButtonBackgroundColor, customButtonTextColor, customTextColor)
}
