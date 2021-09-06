package com.swein.shjetpackcompose.examples.todonote.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

object ToDoNoteView {

    @Composable
    fun ActivityContentView() {

        Scaffold { innerPadding ->
            ContentView(Modifier.padding(innerPadding))
        }
    }

    @Composable
    private fun ContentView(modifier: Modifier) {

    }

}

@Preview(showBackground = true, name = "todo note view")
@Composable
fun ToDoNoteViewPreview() {
//    SHJetpackComposeTheme {
//    }

    ToDoNoteView.ActivityContentView()
}