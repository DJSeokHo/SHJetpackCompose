package com.swein.shjetpackcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.swein.shjetpackcompose.basic.lazycolumnexample.headerandfooter.LazyColumnWithHeaderAndFooterExampleActivity
import com.swein.shjetpackcompose.basic.lazyverticalgridexample.headerandfooter.LazyVerticalGridWithHeaderAndFooterExampleActivity
import com.swein.shjetpackcompose.examples.pdfreaderexample.PDFReaderExampleActivity
import com.swein.shjetpackcompose.examples.viewpagerandlistexample.ViewPagerAndListExampleActivity

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

            val count = remember {
                mutableStateOf(0)
            }

            ClickCounter(
                clicks = count.value,
                onClick = {
                    count.value++
                }
            )
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

//        Intent(this, NavigationAndBottomExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, NavigationAndDrawerExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, SelectableLazyColumnItemExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyColumnSwipeToDismissExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, SwipeableExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyColumnSwipeToRevealMenuExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ShadowButtonExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, MultipleNavigationExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyColumnWithHeaderAndFooterExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyVerticalGridWithHeaderAndFooterExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, PDFReaderExampleActivity::class.java).apply {
//            startActivity(this)
//        }

        Intent(this, ViewPagerAndListExampleActivity::class.java).apply {
            startActivity(this)
        }

    }
}

@Composable
private fun ClickCounter(clicks: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("I've been clicked $clicks times")
    }
}
