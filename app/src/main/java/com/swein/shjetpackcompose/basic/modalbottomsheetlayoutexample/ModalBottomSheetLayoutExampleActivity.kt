package com.swein.shjetpackcompose.basic.modalbottomsheetlayoutexample

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.swein.framework.compose.ripple.RippleWrapper
import com.swein.shjetpackcompose.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ModalBottomSheetLayoutExampleActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            BottomActionSheetWithButton { state, scope ->

                // Make ModalBottomSheetLayout to wrap the activityContentScope

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Image(
                        painterResource(R.drawable.coding_with_cat_icon),
                        null,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .size(200.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.padding(vertical = 20.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                state.animateTo(ModalBottomSheetValue.Expanded, tween(500))
                                //                        state.animateTo(ModalBottomSheetValue.HalfExpanded, tween(500))
                                //                        state.show()
                            }
                        }
                    ) {
                        Text("Toggle Action Sheet")
                    }
                }

                BackHandler(
                    enabled = (state.currentValue == ModalBottomSheetValue.HalfExpanded
                            || state.currentValue == ModalBottomSheetValue.Expanded),
                    onBack = {
                        scope.launch{
                            state.animateTo(ModalBottomSheetValue.Hidden, tween(300))
//                        state.hide()
                        }
                    }
                )
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
                    interactionSource = RippleWrapper.CreateMutableInteractionSource(),
                    indication = RippleWrapper.CreateIndication(true, Color.Red),
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