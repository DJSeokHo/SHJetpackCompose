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
import com.swein.shjetpackcompose.basic.buttonexample.ButtonExampleActivity
import com.swein.shjetpackcompose.basic.card.CardExampleActivity
import com.swein.shjetpackcompose.basic.imageexample.ImageExampleActivity
import com.swein.shjetpackcompose.basic.textexample.TextExampleActivity
import com.swein.shjetpackcompose.basic.textfieldexample.TextFieldExampleActivity
import com.swein.shjetpackcompose.examples.custombottomnavigationbarexample.CustomBottomNavigationBarExampleActivity
import com.swein.shjetpackcompose.examples.lazycolumexample.LazyColumnExampleActivity
import com.swein.shjetpackcompose.examples.marginandpaddingexample.MarginAndPaddingExampleActivity
import com.swein.shjetpackcompose.examples.schedulenote.main.ScheduleListActivity
import com.swein.shjetpackcompose.examples.viewholderexample.ViewHolderExampleActivity
import com.swein.shjetpackcompose.googletutorial.ibasictutorial.GTBasicTutorialActivity
import com.swein.shjetpackcompose.googletutorial.ivbasic.GTBasicActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            SHJetpackComposeTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Android")
//                }
//            }
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

//        Intent(this, MarginAndPaddingExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyColumnExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        ViewHolderExampleActivity.startFrom(this)
//        CustomBottomNavigationBarExampleActivity.startFrom(this)

//        Intent(this, TextExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ButtonExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ImageExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, TextFieldExampleActivity::class.java).apply {
//            startActivity(this)
//        }

        Intent(this, CardExampleActivity::class.java).apply {
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