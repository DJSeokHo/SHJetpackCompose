package com.swein.shjetpackcompose.examples.schedulenote.edit.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.examples.schedulenote.commonpart.CommonView
import com.swein.shjetpackcompose.examples.schedulenote.edit.viewmodel.EditScheduleViewModel

object EditToDoItemView {

    @Composable
    fun ActivityContentView(viewModel: EditScheduleViewModel) {

        Scaffold { // innerPadding ->
//            ContentView(Modifier.padding(innerPadding))
            ContentView(viewModel = viewModel)
        }
    }

    @Composable
    private fun ContentView(modifier: Modifier = Modifier, viewModel: EditScheduleViewModel) {

        Box(
            modifier = modifier.fillMaxSize()
        ) {

            Column(modifier = modifier.fillMaxSize()) {

                // custom tool bar
                ToolBar()

                InputPart(title = viewModel.scheduleTitle.value)
                
            }

            CommonView.Progress(false)

        }

    }
    
    @Composable
    fun ToolBar() {
        CommonView.CustomToolBar(
            startImageResource = R.mipmap.ti_back,
            title = stringResource(id = R.string.schedule_header),
            endImageResource = R.mipmap.ti_save,
            onStartClick = {

            },
            onEndClick = {

            }
        )
    }
    
    @Composable
    fun InputPart(modifier: Modifier = Modifier, viewModel: EditScheduleViewModel) {
        Column(
            modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())) {

            // input area
//            InputTitle(title = )
        }
    }

    @Composable
    fun InputTitle(modifier: Modifier = Modifier, title: String, onValueChange: (String) -> Unit) {

        Column(modifier.fillMaxWidth()) {

            Text(
                text = stringResource(id = R.string.schedule_title_label),
                color = colorResource(id = R.color.basic_color_2022),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            TextField(
                value = title,
                onValueChange = onValueChange
            )

        }

    }

}

@Preview(showBackground = true, name = "edit schedule view")
@Composable
fun EditScheduleViewPreview() {
    EditToDoItemView.ToolBar()
}
