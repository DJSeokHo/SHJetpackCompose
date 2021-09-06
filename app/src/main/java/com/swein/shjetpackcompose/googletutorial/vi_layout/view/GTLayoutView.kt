package com.swein.shjetpackcompose.googletutorial.vi_layout.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.googletutorial.vi_layout.view.constraint_layout.GTLayoutConstraintLayoutView
import com.swein.shjetpackcompose.googletutorial.vi_layout.view.custom.GTLayoutCustomView

object GTLayoutView {

    private const val TAG = "GTLayoutView"

    @Composable
    fun ContentView() {

        ActivityContainer {

//            GTLayoutConstraintLayoutView.ConstraintLayoutView()
//            GTLayoutConstraintLayoutView.ConstraintLayoutViewTwo()
//            GTLayoutConstraintLayoutView.LargeConstraintLayout()
//            GTLayoutConstraintLayoutView.DecoupledConstraintLayout()
            GTLayoutConstraintLayoutView.TwoTexts(text1 = "Hello", text2 = "Coding with cat")

//            GTLayoutCustomView.BodyContent()

//            GTLayoutCustomView.MyOwnColumn(Modifier.padding(8.dp)) {
//                Text("MyOwnColumn")
//                Text("places items")
//                Text("vertically.")
//                Text("We've done it by hand!")
//            }

//            PhotographerCard(it)

//            GTLayoutScrollingListView.ScrollingList()
//            GTLayoutScrollingListView.ImageList()
//            GTLayoutScrollingListView.ColumnWithScrollable()

        }

    }

    @Composable
    fun ActivityContainer(content: @Composable (modifier: Modifier) -> Unit) {

        Scaffold(
            topBar = {
                TopToolBar()
            }
        ) { innerPadding ->
            content(Modifier.padding(innerPadding))
        }
    }

    @Composable
    fun TopToolBar() {

        val context = LocalContext.current

        TopAppBar(

            title = {
                Text(text = "Coding with cat")
            },
            navigationIcon = {
                Image(
                    painter = painterResource(id = R.drawable.coding_with_cat_icon),
                    contentDescription = "Test Image",
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .clickable {
                            Toast
                                .makeText(context, "click navigation icon", Toast.LENGTH_SHORT)
                                .show()
                        }
                )
            },
            actions = {
                IconButton(onClick = {
                    Toast.makeText(context, "click favorite action", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(Icons.Filled.Favorite, contentDescription = null)
                }
            }
        )
    }



    @Composable
    fun PhotographerCard(modifier: Modifier = Modifier) {
        /*
        modifier = Modifier
            // 在设置size之前设置padding相当于外边距
            .padding(10.dp)
            // 此时组件占据空间大小100.dp+外边距 即大小为110.dp*110.dp
            .size(100.dp)设置背景,对应背景来说，在它之前设置的padding 就相当于外边距，所以背景的
            // 在设置size之后设置相当于内边距，组件大小不变
            .padding(10.dp)
            // 设置背景,对应背景来说，在它之前设置的padding 就相当于外边距，所以背景的绘制大小只有90.dp*90.dp
            .background(Color.Gray)
            // 内边距，背景大小不变
            .padding(20.dp)
            // 添加点击事件，同理点击区域的大小90.dp-20.dp 所以可点击局域大小只有70.dp*70.dp
            .clickable(onClick = {})
         */
        Row(
            // as linear layout gravity center in vertical
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                // 这里当于外边距，在background 和 click 之前设置
                .padding(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colors.surface)
                // should set click then add padding
                .clickable {

                }
                // 这里当于内边距，在background 和 click 之后设置
                .padding(16.dp)
        ) {
            // surface as place holder
            Surface(
                modifier = modifier.size(50.dp),
                shape = CircleShape,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
            ) {
                // Image area

            }

            NameAndInfo()
        }

    }

    @Composable
    fun NameAndInfo(modifier: Modifier = Modifier) {

        Column(
            // padding start 8dp
            modifier = modifier.padding(start = 8.dp),
        ) {
            Text(
                text = "Coding with cat",
                fontWeight = FontWeight.Bold
            )

            // LocalContentAlpha is defining opacity level of its children
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = "Android development tutorial", style = MaterialTheme.typography.body2)
            }
        }
        
    }


}

@Preview(showBackground = true, name = "layout")
@Composable
fun DefaultGTLayout() {
    GTLayoutView.ContentView()
}