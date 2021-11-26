package com.swein.shjetpackcompose.basic.scaffold.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

object SearchView {

    @Composable
    fun Content(modifier: Modifier = Modifier) {

        Box(
            modifier = modifier.fillMaxSize().background(Color.Green)
        ) {

        }
    }

}