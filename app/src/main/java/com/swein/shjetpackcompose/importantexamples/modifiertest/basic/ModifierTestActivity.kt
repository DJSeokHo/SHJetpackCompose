package com.swein.shjetpackcompose.importantexamples.modifiertest.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class ModifierTestActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // 这两种写法都行，then直接返回参数
            Modifier.then(Modifier)
            Modifier then Modifier // then 有infix前缀，所以可以这样写。
            // 中缀表示法（忽略该调用的点与圆括号）。
            /*
                中缀函数必须满足以下要求：
                它们必须是成员函数或扩展函数。
                它们必须只有一个参数。
                其参数不得接受可变数量的参数且不能有默认值。
             */

//            Modifier.foldIn() // 先加入的先套用
//            Modifier.foldOut() // 后加入的后套用

            Column {

                // 外部也可以控制 TestView 这个 Composable 的 修饰
                TestView(modifier = Modifier.size(100.dp))

                Spacer(modifier = Modifier.padding(vertical = 10.dp))


                val modifier = Modifier.composed {
                    // 可变，可被监听的 Modifier 方式
                    // 这样写的话，就改变了里面这个Modifier被创建出来的时间节点
                    // 对于显示结果来说，和Modifier的一般写法没有任何区别。

                    // 总结说来，这个 composed工厂函数创建的Modifier是有状态的
                    // 并且就算两个组建同时用了这个状态modifier，那么也是有2个实例
                    // 所以两个组建都是各自管理各自的modifier

                    // 一般开发中不这样写，这个的用途一般用于Modifier的扩展函数
                    // composed 的目的其实是为了创建一个有状态的Modifier，就需要用remember，
                    // 既然要用remember，那么就需要一个compose 上下文环境。
                    val size = remember {
                        mutableStateOf(100.dp)
                    }
                    Modifier
                        .size(size.value)
                        .clickable {
                            size.value = 40.dp
                        }
                }

//                Box(
//                    modifier = modifier
//                        .background(Color.Red)
//                )
//
//                Box(
//                    modifier = modifier
//                        .background(Color.Green)
//                )

                // 这里就用了扩展函数
                Box(
                    modifier = Modifier.customSize()
                        .background(Color.Red)
                )

                Box(
                    modifier = Modifier.customSize()
                        .background(Color.Green)
                )

                Spacer(modifier = Modifier.padding(vertical = 20.dp))
                // 如果不包进Modifier.composed，这样的话两个组建就会共享同一个modifier
                // 而不是为每个组建创建一个单独的modifier实例
                val size = remember {
                    mutableStateOf(100.dp)
                }
                val modifier1 = Modifier
                    .size(size.value)
                    .clickable {
                        size.value = 40.dp
                    }

                Box(
                    modifier = modifier1
                        .background(Color.Red)
                )

                Box(
                    modifier = modifier1
                        .background(Color.Green)
                )
            }



        }
    }
}

@Composable
private fun TestView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.background(Color.Black))
}

fun Modifier.customSize(): Modifier {

    return this.composed {
        val size = remember {
            mutableStateOf(100.dp)
        }
        Modifier
            .size(size.value)
            .clickable {
                size.value = 40.dp
            }
    }
}