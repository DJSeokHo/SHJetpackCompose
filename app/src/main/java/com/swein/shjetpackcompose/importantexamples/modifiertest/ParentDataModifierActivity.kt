package com.swein.shjetpackcompose.importantexamples.modifiertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import com.swein.shjetpackcompose.R

class ParentDataModifierActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            //  这个也符合从右到左生效，左右是右边的父亲的规则，和LayoutModifier, DrawModifier, PointerInputModifier 一致
//            ParentDataModifier

            // weight 就来自于 ParentDataModifier
            // 看名字就知道，weight的作用对象是父组建，也就是Row，在测量时去影响父组建。
            // 所以weight也是个测量函数
            // layoutId 也来自于 ParentDataModifier，用于影响父组建。
            Row {

                Box(
                    modifier = Modifier.size(40.dp).background(Color.Blue).weight(1f)
                )
                Box(
                    modifier = Modifier.size(40.dp).background(Color.Red).layoutId("red box")
                )
                Box(
                    modifier = Modifier.size(40.dp).background(Color.Green)
                )
            }


        }
    }
}