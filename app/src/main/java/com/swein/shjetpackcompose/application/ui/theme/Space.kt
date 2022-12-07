package com.swein.shjetpackcompose.application.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val default: Dp = 0.dp,
    val small: Dp = 8.dp,
    val middle: Dp = 16.dp,
    val large: Dp = 32.dp
)

val LocalSpacing = compositionLocalOf { Spacing() }