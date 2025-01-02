package com.swein.shjetpackcompose.basic.dropdownmenu

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.swein.shjetpackcompose.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
fun DropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    properties: PopupProperties = PopupProperties(focusable = true),
    content: @Composable ColumnScope.() -> Unit
)
 */
class DropdownMenuExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Content()

        }
    }


    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun Content() {

        BottomActionSheetWithButton { bottomActionSheetState, bottomActionSheetScope ->

            // Make ModalBottomSheetLayout to wrap the activityContentScope

            DropDownMenuCompose {

                bottomActionSheetScope.launch {
//                    bottomActionSheetState.animateTo(ModalBottomSheetValue.Expanded, tween(500))
                    bottomActionSheetState.show()
                    //                        state.animateTo(ModalBottomSheetValue.HalfExpanded, tween(500))
                    //                        state.show()
                }

            }

            BackHandler(
                enabled = (bottomActionSheetState.currentValue == ModalBottomSheetValue.HalfExpanded
                        || bottomActionSheetState.currentValue == ModalBottomSheetValue.Expanded),
                onBack = {
                    bottomActionSheetScope.launch{
//                        bottomActionSheetState.animateTo(ModalBottomSheetValue.Hidden, tween(300))
                        bottomActionSheetState.hide()
                    }
                }
            )
        }

    }

    @Composable
    private fun DropDownMenuCompose(onDropDownMenuItemSelect: () -> Unit) {

        var expanded by remember {
            mutableStateOf(false)
        }

        val items = listOf("Coding", "With", "Cat", "Android", "iOS", "Python")
        var selectedIndex by remember {
            mutableStateOf(0)
        }

        Box {

            TextButton(
                onClick = {
                    expanded = true
                },
                colors = ButtonDefaults.textButtonColors(backgroundColor = Color.Red, contentColor = Color.White)
            ) {
                Text(
                    text = items[selectedIndex]
                )
            }

            DropDownMenu(
                expanded = expanded,
                dropDownMenuItems = items,
                onDropDownMenuItemSelect = {
                    selectedIndex = it
                    expanded = false

                    onDropDownMenuItemSelect()
                },
                onDismissRequest = {
                    expanded = false
                }
            )
        }
    }


    @Composable
    private fun DropDownMenu(
        expanded: Boolean,
        dropDownMenuItems: List<String>,
        onDropDownMenuItemSelect: (selectedIndex: Int) -> Unit,
        onDismissRequest: () -> Unit
    ) {

        DropdownMenu(
            offset = DpOffset(0.dp, 20.dp),
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            modifier = Modifier
                .wrapContentSize()
                .background(Color.LightGray)
                .padding(all = 20.dp)
        ) {

            dropDownMenuItems.forEachIndexed { index, text ->
                DropdownMenuItem(onClick = {
                    onDropDownMenuItemSelect(index)
                }) {

                    if (index == 3) {

                        // implementation 'io.coil-kt:coil-compose:2.0.0-alpha05'
                        Image(
//            painter = rememberAsyncImagePainter(
//                "https://developer.android.com/images/brand/Android_Robot.png"
//            ),
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(context = LocalContext.current)
                                    .crossfade(true)
                                    .data("https://developer.android.com/images/brand/Android_Robot.png")
                                    .build(),
                                filterQuality = FilterQuality.High
                            ),
//                            painter = rememberImagePainter(
//                                data = "https://developer.android.com/images/brand/Android_Robot.png"
//                            ),
                            contentDescription = "Android Logo",
                            modifier = Modifier.size(30.dp)
                        )

                        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
                    }

                    Text(text = text, color = Color.DarkGray)
                }
            }

        }
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun BottomActionSheetWithButton(
        activityContentScope: @Composable (state: ModalBottomSheetState, scope: CoroutineScope) -> Unit
    ) {

        val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()

        val context = LocalContext.current

        ModalBottomSheetLayout(
            sheetBackgroundColor = Color.White,
            sheetElevation = 5.dp,
            sheetShape = RoundedCornerShape(topStart = 30.dp),
            sheetState = state,
            sheetContent = {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {

                    Surface(
                        color = Color.DarkGray
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Text("Bottom Sheet", color = Color.White, modifier = Modifier.padding(end = 10.dp))
                        }

                    }

                    Spacer(modifier = Modifier.padding(vertical = 16.dp))

                    SheetItem(context = context, imageResource = R.drawable.coding_with_cat_icon, text = "Coding")

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    SheetItem(context = context, imageResource = R.drawable.coding_with_cat_icon, text = "With")

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    SheetItem(context = context, imageResource = R.drawable.coding_with_cat_icon, text = "Cat")
                }
            }
        ) {
            activityContentScope(state, scope)
        }

    }

    @Composable
    private fun SheetItem(context: Context, imageResource: Int, text: String) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = LocalIndication.current,
                    onClick = {
                        Toast
                            .makeText(context, text, Toast.LENGTH_SHORT)
                            .show()
                    }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painterResource(imageResource),
                null,
                modifier = Modifier
                    .padding(4.dp)
                    .width(40.dp)
                    .height(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.padding(horizontal = 10.dp))

            Text(text)

        }

    }
}