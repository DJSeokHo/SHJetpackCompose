package com.swein.shjetpackcompose.examples.todonote.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

object ToDoDetailView {

    @Composable
    fun FragmentContentView(contentView: @Composable (modifier: Modifier) -> Unit) {

        Scaffold { innerPadding ->
            contentView(Modifier.padding(innerPadding))
        }
    }
}

@Preview(showBackground = true, name = "todo note view")
@Composable
fun ToDoDetailViewPreview() {

    ToDoDetailView.FragmentContentView { _ ->

    }
}