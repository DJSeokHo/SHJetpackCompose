package com.swein.shjetpackcompose.basic.textexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TextExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            TextExamples()

        }
    }

    @Composable
    private fun TextExamples() {

        Column {

            JustText()

            Spacer(
                Modifier
                    .padding(vertical = 5.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Black))

            StyleText()

            Spacer(
                Modifier
                    .padding(vertical = 5.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Black))

            FontText()

            Spacer(
                Modifier
                    .padding(vertical = 5.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Black))

            TextOverflow()

            Spacer(
                Modifier
                    .padding(vertical = 5.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Black))

            TextWithTextAlign()

            Spacer(
                Modifier
                    .padding(vertical = 5.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Black))

            TextLineHeight()

            Spacer(
                Modifier
                    .padding(vertical = 5.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Black))

            TextWithClick()

            Spacer(
                Modifier
                    .padding(vertical = 5.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Black))

            TextSpanRange()

            Spacer(
                Modifier
                    .padding(vertical = 5.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Black))

            TextSpanRangeClick()

            Spacer(
                Modifier
                    .padding(vertical = 5.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Black))

            TextSelection()

            Spacer(
                Modifier
                    .padding(vertical = 5.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Black))

            TextCenter()
        }

    }

    @Composable
    private fun JustText() {
        Text(text = "Coding with cat")
    }

    @Composable
    private fun StyleText() {
        Text(
            text = "Coding with cat",
//            style = MaterialTheme.typography.h3
            style = MaterialTheme.typography.body1
        )
    }

    @Composable
    private fun FontText() {
        Text(
            text = "Coding with cat",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                letterSpacing = 10.sp
            )
        )
    }

    @Composable
    private fun TextOverflow() {
        Text(
            text = "Coding with cat Coding with cat Coding with cat Coding with cat Coding with cat Coding with cat Coding with cat Coding with cat",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body2
        )
    }

    /**
     * we should fill max width or set a width dp long than text length before this
     * same as gravity
     */
    @Composable
    private fun TextWithTextAlign() {

        Column {

            Text(
                text = "coding with cat",
                color = Color.Red,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Spacer(Modifier.padding(vertical = 5.dp))

            Text(
                text = "coding with cat",
                color = Color.Green,
                modifier = Modifier.width(300.dp),
                textAlign = TextAlign.End
            )
        }
    }

    @Composable
    private fun TextLineHeight() {

        Text(
            text = "Coding with cat Coding with cat Coding with cat Coding with cat Coding with cat Coding with cat Coding with cat Coding with cat",
            style = MaterialTheme.typography.body2,
            lineHeight = 40.sp
        )

    }

    @Composable
    private fun TextWithClick() {

        val context = LocalContext.current

        Column {

            Text(
                text = "Subscribe",
                // In here, padding is same as margin
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        Toast.makeText(context, "subscribe coding with cat", Toast.LENGTH_SHORT)
                            .show()
                    }
            )

            Spacer(Modifier.padding(vertical = 5.dp))

            Text(
                text = "Subscribe",
                // after setting click listener, now the padding is same as padding
                modifier = Modifier
                    .clickable {
                        Toast
                            .makeText(context, "subscribe coding with cat", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .padding(10.dp)
            )
        }

    }

    @Composable
    private fun TextSpanRange() {

        Text(
            buildAnnotatedString {
                append("Coding with ")
                withStyle(style = SpanStyle(color = Color.Red, fontWeight = FontWeight.ExtraBold, fontStyle = FontStyle.Italic)) {
                    append("Cat")
                }
                append(" ")
                withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.ExtraBold)) {
                    append("is good")
                }
            }
        )

    }

    @Composable
    private fun TextSpanRangeClick() {

        val context = LocalContext.current

        val spanRangeClickText = buildAnnotatedString {
            append("Coding with cat, ")
            pushStringAnnotation(
                tag = "tag",
                annotation = "Subscribe coding with cat is very useful"
            )
            withStyle(
                style = SpanStyle(
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("Subscribe")
            }
            pop()
        }

        ClickableText(
            text = spanRangeClickText,
            onClick = { offset ->
                spanRangeClickText.getStringAnnotations(
                    tag = "tag",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let { annotation ->
                    Toast.makeText(context, annotation.item, Toast.LENGTH_SHORT).show()
                }
            }
        )

    }

    @Composable
    private fun TextSelection() {

        SelectionContainer {
            Column{
                Text(
                    text = "Coding with cat",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

            }
        }

    }

    /**
     * Text in parent's center
     */
    @Composable
    private fun TextCenter() {

        Box(
            modifier = Modifier.size(width = 200.dp, height = 100.dp).background(Color.Cyan),
            contentAlignment = Alignment.Center
        ) {
            Text("Coding with cat")
        }
    }

}
