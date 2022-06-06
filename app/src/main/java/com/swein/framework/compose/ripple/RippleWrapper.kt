package com.swein.framework.compose.ripple

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

object RippleWrapper: RippleTheme {

    @Composable
    override fun defaultColor() =
        RippleTheme.defaultRippleColor(
            Color.White,
            lightTheme = true
        )

    @Composable
    override fun rippleAlpha(): RippleAlpha =
        RippleTheme.defaultRippleAlpha(
            Color.Black,
            lightTheme = true
        )

    @Composable
    fun Ripple(composeView: @Composable () -> Unit) {

        CompositionLocalProvider(LocalRippleTheme provides RippleWrapper) {
            composeView()
        }
    }

    @Composable
    fun CreateMutableInteractionSource(): MutableInteractionSource = remember {
        MutableInteractionSource()
    }

    @Composable
    fun CreateIndication(bounded: Boolean = true, color: Color = Color.Gray) = rememberRipple(bounded = bounded, color = color)

}