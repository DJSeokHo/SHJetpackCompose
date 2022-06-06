package com.swein.shjetpackcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.swein.shjetpackcompose.application.ui.theme.SHJetpackComposeTheme
import com.swein.shjetpackcompose.basic.alertdialogexample.AlertDialogExampleActivity
import com.swein.shjetpackcompose.basic.bottomnavigationexample.BottomNavigationExampleActivity
import com.swein.shjetpackcompose.basic.cameraandphoto.CameraAndPhotoActivity
import com.swein.shjetpackcompose.basic.composetoxml.ComposeToXMLExampleActivity
import com.swein.shjetpackcompose.basic.compositionlocalexample.CompositionLocalExampleActivity
import com.swein.shjetpackcompose.basic.customthemeexample.CustomThemeExampleActivity
import com.swein.shjetpackcompose.basic.dropdownmenu.DropdownMenuExampleActivity
import com.swein.shjetpackcompose.basic.lazyrowexample.LazyRowExampleActivity
import com.swein.shjetpackcompose.basic.modaldrawer.ModalDrawerExampleActivity
import com.swein.shjetpackcompose.basic.navigationandbottomexample.NavigationAndBottomExampleActivity
import com.swein.shjetpackcompose.basic.navigationanddrawerexample.NavigationAndDrawerExampleActivity
import com.swein.shjetpackcompose.basic.navigationexample.NavigationExampleActivity
import com.swein.shjetpackcompose.basic.textfieldexample.TextFieldExampleActivity
import com.swein.shjetpackcompose.basic.webviewexample.WebViewExampleActivity
import com.swein.shjetpackcompose.basic.xmltocompose.XMLToComposeExampleActivity

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

//        Intent(this, CardExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, SurfaceExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, RowExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ColumnExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, BoxExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ModifierExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, AlertDialogExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, CustomAlertDialogExampleActivity::class.java).apply {
//            startActivity(this)
//        }


//        Intent(this, ModalBottomSheetLayoutExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ModalDrawerExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, TopAppBarExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, DropdownMenuExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, BottomNavigationExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, FloatingActionButtonExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyColumnExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyRowExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyVerticalGridExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ConstraintExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyColumnStickerHeaderExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyColumnReloadAndLoadMoreExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ScaffoldExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, TabRowExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, PagerExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, TabRowPagerExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, CompositionLocalExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, CustomThemeExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ComposeToXMLExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, XMLToComposeExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, WebViewExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, CameraAndPhotoActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, NavigationExampleActivity::class.java).apply {
//            startActivity(this)
//        }

        Intent(this, NavigationAndBottomExampleActivity::class.java).apply {
            startActivity(this)
        }

//        Intent(this, NavigationAndDrawerExampleActivity::class.java).apply {
//            startActivity(this)
//        }
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