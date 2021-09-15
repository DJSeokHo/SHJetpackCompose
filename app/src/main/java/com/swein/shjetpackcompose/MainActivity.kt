package com.swein.shjetpackcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.swein.shjetpackcompose.application.ui.theme.SHJetpackComposeTheme
import com.swein.shjetpackcompose.examples.lazycolumexample.LazyColumnExampleActivity
import com.swein.shjetpackcompose.examples.schedulenote.main.ScheduleListActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SHJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }

//        Intent(this, GTBasicTutorialActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, GTBasicActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, GTLayoutActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ScheduleListActivity::class.java).apply {
//            startActivity(this)
//        }

        Intent(this, LazyColumnExampleActivity::class.java).apply {
            startActivity(this)
        }


    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SHJetpackComposeTheme {
        Greeting("Android")
    }
}