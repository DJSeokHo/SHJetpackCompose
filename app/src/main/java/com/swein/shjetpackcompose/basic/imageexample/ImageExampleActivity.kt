package com.swein.shjetpackcompose.basic.imageexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.application.ui.theme.Shapes
import com.swein.shjetpackcompose.examples.schedulenote.commonpart.CommonView
import com.swein.shjetpackcompose.examples.schedulenote.edit.view.EditToDoItemView

class ImageExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ImageExamples()

        }
    }

    @Composable
    private fun ImageExamples() {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {

            ImageResource()

            SplitLine()

            ShapeImage()

            SplitLine()

            ImageUrl()

            SplitLine()

            ImageSurfaceShape()
        }
    }

    @Composable
    private fun ImageResource() {

        Column {

            Image(
                painter = painterResource(id = R.drawable.test_image_1),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.padding(vertical = 3.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.test_image_1),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(width = 120.dp, height = 120.dp)
                        .background(Color.Cyan)
                )

                Spacer(modifier = Modifier.padding(horizontal = 3.dp))

                Image(
                    painter = painterResource(id = R.drawable.test_image_1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 120.dp, height = 120.dp)
                        .background(Color.Cyan)
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 3.dp))

            Image(
                painter = painterResource(id = R.drawable.test_image_1),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @Composable
    private fun ShapeImage() {

        val context = LocalContext.current

        Image(
            painter = painterResource(id = R.drawable.coding_with_cat_icon),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
                .clickable {
                    Toast
                        .makeText(context, "subscribe ~~", Toast.LENGTH_SHORT)
                        .show()
                }
        )

    }

    @Composable
    private fun ImageUrl() {
        /*
        implementation 'io.coil-kt:coil-compose:2.0.0-alpha02'
         */
        Image(
            painter = rememberImagePainter(
                data = "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(150.dp)
        )
    }

    @Composable
    private fun ImageSurfaceShape() {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            Surface(
                shape = CircleShape,
                color = Color.Cyan
            ) {
                Image(
                    painter = painterResource(id = R.drawable.test_image_1),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(modifier = Modifier.padding(horizontal = 3.dp))

            Surface(
                shape = CircleShape,
                border = BorderStroke(5.dp, Color.LightGray)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.test_image_1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(120.dp)
                )
            }
        }
    }

    @Composable
    private fun SplitLine() {
        Spacer(
            Modifier
                .padding(vertical = 3.dp)
                .height(5.dp)
                .fillMaxWidth()
                .background(Color.Red))
    }

}