package com.swein.shjetpackcompose.basic.card

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.swein.shjetpackcompose.R

class CardExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            CardExamples()

        }
    }

    @Composable
    private fun CardExamples() {

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {

            CardExample1()

            SplitLine()

            CardExample2()

            SplitLine()

            CardExample3()
        }
    }

    @Composable
    private fun CardExample1() {

        val context = LocalContext.current

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(
                    /*
                    this is custom ripple effect
                     */
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(bounded = true, color = Color.Red),
                    onClick = {
                        Toast
                            .makeText(context, "Card Example 1", Toast.LENGTH_SHORT)
                            .show()
                    }
                ),
            backgroundColor = colorResource(id = R.color.ceeeeee),
            elevation = 8.dp
        ) {

            Text(
                modifier = Modifier.padding(16.dp),
                text = "Coding with cat"
            )
        }

    }

    @Composable
    private fun CardExample2() {

        val context = LocalContext.current

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(
                    /*
                    this is custom ripple effect
                     */
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(bounded = true, color = Color.Red),
                    onClick = {
                        Toast
                            .makeText(context, "Card Example 2", Toast.LENGTH_SHORT)
                            .show()
                    }
                ),
            backgroundColor = colorResource(id = R.color.ceeeeee),
            elevation = 8.dp
        ) {


            Box(
                modifier = Modifier.padding(16.dp),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.coding_with_cat_icon),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                )
            }

        }

    }

    @Composable
    private fun CardExample3() {

        val context = LocalContext.current

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(
                    /*
                    this is custom ripple effect
                     */
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(bounded = true, color = Color.Red),
                    onClick = {
                        Toast
                            .makeText(context, "Card Example 3", Toast.LENGTH_SHORT)
                            .show()
                    }
                ),
            backgroundColor = colorResource(id = R.color.ceeeeee),
            elevation = 8.dp
        ) {

            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = R.drawable.coding_with_cat_icon),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.padding(horizontal = 10.dp))

                Column {
                    Text(
                        buildAnnotatedString {
                            append("Coding with ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Blue)
                            ) {
                                append("Cat")
                            }
                        }
                    )

                    Spacer(modifier = Modifier.padding(vertical = 2.dp))

                    Text(
                        buildAnnotatedString {
                            append("Android development ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, fontStyle = FontStyle.Italic)) {
                                append("tutorial")
                            }
                        }
                    )
                }

            }

        }
    }

    @Composable
    private fun SplitLine() {
        Spacer(
            Modifier
                .padding(vertical = 10.dp)
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.Black))
    }
}