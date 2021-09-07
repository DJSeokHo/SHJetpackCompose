package com.swein.shjetpackcompose.examples.todonote.main.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.compose.rememberImagePainter
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.examples.todonote.commonpart.CommonView
import com.swein.shjetpackcompose.examples.todonote.model.ToDoItemDataModel

object ToDoNoteView {

    @Composable
    fun ActivityContentView() {

        Scaffold(
            backgroundColor = Color.White
        ) { // innerPadding ->
//            ContentView(Modifier.padding(innerPadding))
            ContentView()
        }
    }

    @Composable
    private fun ContentView(modifier: Modifier = Modifier) {

        Box(
            modifier = modifier.fillMaxSize()
        ) {

            Column(modifier = modifier.fillMaxSize()) {

                // custom tool bar
//                CommonView.CustomToolBar(endImageResource = R.mipmap.ti_plus) {
//

//                }

                ListItemView()

            }

        }

    }

    @Composable
    private fun ListView() {

    }

    @Composable
    private fun ListItemView(
        modifier: Modifier = Modifier//,
//        toDoItemDataModel: ToDoItemDataModel
    ) {

        Surface(
            modifier = modifier
                .fillMaxWidth()
                .height(130.dp)
                .padding(10.dp),
            elevation = 5.dp,
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {

            Row(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .clickable {

                    }
                    .padding(8.dp),
            ) {

                Surface(
                    modifier = modifier.size(60.dp),
//                    shape = CircleShape,
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
                ) {
                    // Image area
                    Image(
                        painter = rememberImagePainter(
                            data = "https://developer.android.com/images/brand/Android_Robot.png"
                        ),
                        contentDescription = "Android Logo",
                        modifier = Modifier.size(60.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                ConstraintLayout(
                    listItemConstraintSet(),
                    modifier = modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(start = 10.dp)
                ) {

                    Text(
                        text = "title",
                        color = colorResource(id = R.color.c111111),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = modifier.layoutId("title")
                    )

                    Text(
                        text = "contenttitletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitle",
                        color = colorResource(id = R.color.c666666),
                        fontSize = 16.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = modifier.layoutId("content"),
                    )

                    Text(
                        text = "0000-00-00 00:00:00",
                        color = colorResource(id = R.color.c999999),
                        fontSize = 10.sp,
                        maxLines = 1,
                        modifier = modifier.layoutId("date")
                    )

                    Row(
                        modifier = modifier.layoutId("state")
                    ) {

                        Image(
                            painter = painterResource(id = R.mipmap.ti_finished),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = modifier.padding(end = 4.dp).size(16.dp)
                        )

                        Image(
                            painter = painterResource(id = R.mipmap.ti_important),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = modifier.padding(end = 4.dp).size(16.dp)
                        )

                        Image(
                            painter = painterResource(id = R.mipmap.ti_urgent),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = modifier.padding(end = 4.dp).size(16.dp)
                        )

                    }

                }

            }

        }

    }

    private fun listItemConstraintSet(): ConstraintSet {
        return ConstraintSet {
            val title = createRefFor("title")
            val content = createRefFor("content")
            val date = createRefFor("date")

            val state = createRefFor("state")

            constrain(title) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }

            constrain(content) {
                top.linkTo(title.bottom, margin = 2.dp)
                start.linkTo(title.start)
            }

            constrain(date) {
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }

            constrain(state) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }

        }
    }
}

@Preview(showBackground = true, name = "todo note view")
@Composable
fun ToDoNoteViewPreview() {
//    SHJetpackComposeTheme {
//    }

    ToDoNoteView.ActivityContentView()

}