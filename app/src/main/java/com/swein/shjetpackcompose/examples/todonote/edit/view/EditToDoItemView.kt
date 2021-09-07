package com.swein.shjetpackcompose.examples.todonote.edit.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.swein.shjetpackcompose.examples.todonote.main.view.ToDoNoteView

object EditToDoItemView {

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

@Preview(showBackground = true, name = "edit todo item view")
@Composable
fun EditToDoItemViewPreview() {
//    SHJetpackComposeTheme {
//    }

    EditToDoItemView.ActivityContentView()
}