package com.swein.shjetpackcompose.googletutorial.vi_layout.view.custom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object GTLayoutCustomView {

    private val topics = listOf(
        "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
        "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
        "Religion", "Social sciences", "Technology", "TV", "Writing"
    )

    @Composable
    fun Chip(modifier: Modifier = Modifier, text: String) {
        
        Card(
            modifier = modifier,
            border = BorderStroke(color = Color.Black, width = Dp.Hairline),
            shape = RoundedCornerShape(8.dp)
        ) {

            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(color = MaterialTheme.colors.secondary)
                )

                Spacer(modifier = Modifier.width(4.dp))
                Text(text = text)
            }

        }
    }

    @Composable
    fun BodyContent(modifier: Modifier = Modifier) {
        Row(modifier = modifier.horizontalScroll(rememberScrollState())) {
            StaggeredGrid(modifier = modifier) {
//            StaggeredGrid(modifier = modifier, rows = 5) {
                for (topic in topics) {
                    Chip(modifier = Modifier.padding(8.dp), text = topic)
                }
            }
        }
    }

    @Composable
    fun StaggeredGrid(
        modifier: Modifier = Modifier,
        rows: Int = 3,
        content: @Composable () -> Unit
    ) {

        // set modifier and content
        Layout(
            modifier = modifier,
            content = content
        ) { measureables, constraints ->
            // measure and position children given constraints logic here

            // Keep track of the width of each row
            val rowWidths = IntArray(rows) {0}

            // Keep track of the max height of each row
            val rowHeights = IntArray(rows) {0}


            // Don't constrain child views further, measure them with given constraints
            // List of measured children
            val placeables = measureables.mapIndexed { index, measurable ->

                // Measure each child
                val placeable = measurable.measure(constraints = constraints)

                val row = index % rows

                rowWidths[row] += placeable.width
                rowHeights[row] = if (rowHeights[row] >= placeable.height) rowHeights[row] else placeable.height

                // return the child
                placeable
            }

            // Grid's width is the widest row
            val width = rowWidths.maxOrNull()
                ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth

            // Grid's height is the sum of the tallest element of each row
            // coerced to the height constraints
            val height = rowHeights.sumOf { it }
                .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

            // Y of each row, based on the height accumulation of previous rows
            val rowY = IntArray(rows) { 0 }
            for (i in 1 until rows) {
                rowY[i] = rowY[i-1] + rowHeights[i-1]
            }

            // In Layout, must return a layout with width and height
            layout(width, height) {
                // x cord we have placed up to, per row
                val rowX = IntArray(rows) { 0 }

                placeables.forEachIndexed { index, placeable ->

                    val row = index % rows

                    placeable.placeRelative(x = rowX[row], y = rowY[row])
                    rowX[row] += placeable.width
                }
            }
        }
    }

    @Composable
    fun MyOwnColumn(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) {
        Layout(
            modifier = modifier,
            content = content
        ) { measurables, constraints ->
            // Don't constrain child views further, measure them with given constraints
            // List of measured children
            val placeables = measurables.map { measurable ->
                // Measure each child
                measurable.measure(constraints)
            }

            // Track the y co-ord we have placed children up to
            var yPosition = 0

            // Set the size of the layout as big as it can
            layout(constraints.maxWidth, constraints.maxHeight) {
                // Place children in the parent layout
                placeables.forEach { placeable ->
                    // Position item on the screen
                    placeable.placeRelative(x = 0, y = yPosition)
                    // Record the y co-ord placed up to
                    yPosition += placeable.height
                }
            }
        }
    }

}

@Preview(showBackground = true, name = "layout custom view")
@Composable
fun DefaultGTLayoutCustomView() {

    GTLayoutCustomView.BodyContent()
//    GTLayoutCustomView.Chip(text = "Hi there")

//    GTLayoutCustomView.MyOwnColumn(Modifier.padding(8.dp)) {
//        Text("MyOwnColumn")
//        Text("places items")
//        Text("vertically.")
//        Text("We've done it by hand!")
//    }
}
