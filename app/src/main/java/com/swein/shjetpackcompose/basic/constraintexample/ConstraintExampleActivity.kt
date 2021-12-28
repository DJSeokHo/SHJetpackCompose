package com.swein.shjetpackcompose.basic.constraintexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.application.ui.theme.SHJetpackComposeTheme

class ConstraintExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val context = LocalContext.current

            SHJetpackComposeTheme(false) {

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    CustomTopAppBarView(
                        onStartClick = {
                            Toast.makeText(context, "click back", Toast.LENGTH_SHORT).show()
                        },
                        onEndClick = {
                            Toast.makeText(context, "click close", Toast.LENGTH_SHORT).show()
                        }
                    )

                    ProfileView()
                }
            }
        }
    }

    @Composable
    private fun CustomTopAppBarView(
        onStartClick: () -> Unit,
        onEndClick: () -> Unit
    ) {

        Surface(
            color = colorResource(id = R.color.basic_color_2022),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .layoutId("customTopBarConstraint"),
            elevation = 10.dp
        ) {

            ConstraintLayout(
                constraintSet = customTopAppBarViewConstraintSet(),
                modifier = Modifier.fillMaxSize()
            ) {

                Image(
                    painter = painterResource(id = R.mipmap.ti_back),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(40.dp)
                        .layoutId("imageStartConstraint")
                        .clip(CircleShape)
                        .clickable {
                            onStartClick()
                        }
                        .padding(6.dp)
                )

                Text(
                    text = "Coding with cat",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.layoutId("textLabelConstraint")
                )

                Image(
                    painter = painterResource(id = R.mipmap.ti_close),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(40.dp)
                        .layoutId("imageEndConstraint")
                        .clip(CircleShape)
                        .clickable {
                            onEndClick()
                        }
                        .padding(6.dp)
                )

            }

        }

    }

    private fun customTopAppBarViewConstraintSet(): ConstraintSet {

        return ConstraintSet {

            val imageStartConstraint = createRefFor("imageStartConstraint")
            val textLabelConstraint = createRefFor("textLabelConstraint")
            val imageEndConstraint = createRefFor("imageEndConstraint")

            constrain(imageStartConstraint) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start, margin = 16.dp)
            }

            constrain(textLabelConstraint) {

                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)

                start.linkTo(imageStartConstraint.end, margin = 6.dp)
                end.linkTo(imageEndConstraint.start, margin = 6.dp)
            }

            constrain(imageEndConstraint) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end, margin = 16.dp)
            }

        }
    }

    @Composable
    private fun ProfileView() {

        Surface(
            color = colorResource(id = R.color.cdddddd),
            modifier = Modifier.fillMaxSize().layoutId("profileConstraint"),
        ) {
            ConstraintLayout(
                constraintSet = profileViewConstraintSet()
            ) {

                Image(
                    painter = painterResource(id = R.drawable.coding_with_cat_icon),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(150.dp)
                        .layoutId("imageConstraint")
                        .clip(CircleShape)
                )

                Text(
                    text = "Subscribe Coding with cat on Youtube is helpful",
                    color = Color.DarkGray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.layoutId("textInfoConstraint")
                )

                Button(
                    onClick = {
                        Toast.makeText(this, "Subscribe", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.layoutId("buttonConstraint"),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red, contentColor = Color.White)
                ) {
                    Text(
                        text = "Subscribe",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

            }
        }
    }

    private fun profileViewConstraintSet(): ConstraintSet {

        return ConstraintSet {

            val imageConstraint = createRefFor("imageConstraint")
            val textInfoConstraint = createRefFor("textInfoConstraint")
            val buttonConstraint = createRefFor("buttonConstraint")

            constrain(imageConstraint) {
                top.linkTo(parent.top, margin = 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }

            constrain(textInfoConstraint) {

                top.linkTo(imageConstraint.bottom, margin = 15.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            }

            constrain(buttonConstraint) {

                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, margin = 16.dp)
            }

        }
    }
}