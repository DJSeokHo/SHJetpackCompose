package com.swein.shjetpackcompose.examples.todonote.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

object ToDoNoteView {

    @Composable
    fun ActivityContentView(contentView: @Composable (modifier: Modifier) -> Unit) {

        Scaffold { innerPadding ->
            contentView(Modifier.padding(innerPadding))
        }
    }
}

@Preview(showBackground = true, name = "todo note view")
@Composable
fun ToDoNoteViewPreview() {
//    SHJetpackComposeTheme {
//    }

    ToDoNoteView.ActivityContentView { _ ->

    }
}