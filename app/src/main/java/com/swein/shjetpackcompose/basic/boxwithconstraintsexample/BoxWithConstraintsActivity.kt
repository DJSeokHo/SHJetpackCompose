package com.swein.shjetpackcompose.basic.boxwithconstraintsexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.swein.shjetpackcompose.R

class BoxWithConstraintsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ContentView()
        }
    }
}

@Composable
private fun ContentView() {

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (maxWidth < maxHeight) {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.coding_with_cat_icon),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                    )

                    Text(
                        text = "Coding with cat",
                        color = Color.Black
                    )
                }
            }
            else {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.coding_with_cat_icon),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                    )

                    Text(
                        text = "Coding with cat",
                        color = Color.Black
                    )
                }
            }
        }

    }
}