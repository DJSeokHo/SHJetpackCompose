package com.swein.shjetpackcompose.examples.todonote.edit.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.swein.shjetpackcompose.examples.todonote.view.ToDoNoteView

object EditToDoItemView {

    @Composable
    fun ActivityContentView(contentView: @Composable (modifier: Modifier) -> Unit) {

        Scaffold { innerPadding ->
            contentView(Modifier.padding(innerPadding))
        }
    }
}

@Preview(showBackground = true, name = "edit todo item view")
@Composable
fun EditToDoItemViewPreview() {
//    SHJetpackComposeTheme {
//    }

    EditToDoItemView.ActivityContentView { _ ->

    }
}