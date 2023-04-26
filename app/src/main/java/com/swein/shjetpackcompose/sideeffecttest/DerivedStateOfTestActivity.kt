package com.swein.shjetpackcompose.sideeffecttest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

class DerivedStateOfTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ExampleFour()
//            ExampleThree()
//            ExampleTwo()
//            ExampleOne()
        }
    }
}

@Composable
private fun ExampleFour() {

    val names = remember {
        mutableStateListOf("Coding with cat Android", "Coding with cat iOS")
    }


    ProcessedNames(names = names) {
        names.add("Coding with cat Python")
    }
}

@Composable
private fun ProcessedNames(names: List<String>, onClick: () -> Unit) {

    // 完美解决
    // derivedStateOf 用于 names内部的值变化后可以被监听到从而刷新UI
    // names作为remember的参数，用于 names 这个对象自身被新的对象赋值时可以被监听到从而刷新UI
    // 官方给出的例子也是一样的写法，双保险
    val uppercaseProcessedNames = remember(names) {
        derivedStateOf {
            names.map {
                it.uppercase()
            }
        }
    }

    Column {
        for (item in uppercaseProcessedNames.value) {

            Text(
                modifier = Modifier
                    .clickable {
                        onClick()
                    },
                text = item
            )

        }
    }

}


@Composable
private fun ExampleThree() {

    val name = remember {
        mutableStateOf("Coding with cat Android")
    }

    ProcessedName(name = name.value) {
        name.value = "Coding with cat iOS"
    }
}

// 一般来说，实际项目中不会把状态当成参数，这不符合通用原则
@Composable
private fun ProcessedName(name: String, onClick: () -> Unit) {

    // 这样抽离到函数的方式，方法2继续可以更新，方法1反而无法更新了，为什么？
    // 因为进到函数的其实不是name本身，而是他的状态代理的值。进来后他就被剥掉了代理，
    // 也就是一个无状态的值而已，所以，derivedStateOf肯定就无法执行，因为它需要监听一个状态，
    // 而不是监听一个值。
    // 而此时方法而监听name这个值，发现他变了，所以就执行，用新的值刷新UI。
    // 区别？ remember的参数是一个值，监听一个值，值变了就执行。而derivedStateOf是监听
    // 一个状态，值对derivedStateOf是无效的。

    // 所以在函数中一般使用写法2

    // 写法 1
//    val uppercaseProcessedName = remember {
//        derivedStateOf {
//            name.uppercase()
//        }
//    }

    // 写法 2
    val uppercaseProcessedName = remember(name) {
        name.uppercase()
    }

    Text(
        modifier = Modifier
            .clickable {
                onClick()
            },
        text = uppercaseProcessedName
    )

}

@Composable
private fun ExampleTwo() {

    /**
     * remember 的用途就是让发生重组后，没有变化的数据就跳过重组，提高性能
     * 而State的用途是让变化过的数据去标记用到数据的UI，然后在触发重组后刷新UI
     * 这两大核心功能相辅相承，
     * 无参数的remember只会在初始化时执行一次。
     */

    val names = remember {
        mutableStateListOf("Coding with cat Android", "Coding with cat iOS")
    }

    // 写法 1
    // processedName 依赖 name, name改变了，processedName监测到，对其进行大写处理，然后更新界面。
    // 这里 remember没有参数，这样的话就算name的值改变了，remember括号里的部分再重组中是不会执行的，
    // 但是套了个 derivedStateOf，所以就可以监听name的值改变了。
    val uppercaseProcessedNames = remember {
        derivedStateOf {
            names.map {
                it.uppercase()
            }
        }
    }

    // 写法 2
    // 在这个例子里，这种写法就不行了，names改变了也不会刷新界面
    // 因为names如果改变了，肯定触发更新，但是改变的是names的值，names这个对象自己并没有改变，
    // 所以这里remember括号里的部分就不会执行，哪怕names的值更新了。
    // 而之前的那个例子用的是name作为参数，是一个状态值，所以值改变，肯定就触发更新了。
//    val uppercaseProcessedNames = remember(names) {
//        names.map {
//            it.uppercase()
//        }
//    }

    Column {
        for (item in uppercaseProcessedNames.value) {

            Text(
                modifier = Modifier
                    .clickable {
                        names.add("Coding with cat Python")
                    },
                text = item
            )

        }
    }

}

@Composable
private fun ExampleOne() {

    val name = remember {
        mutableStateOf("Coding with cat Android")
    }

    // 写法 1
    // processedName 依赖 name
    // 这里 remember没有参数，这样的话就算name的值改变了，remember括号里的部分再重组中是不会执行的，
    // 但是套了个 derivedStateOf，所以就可以监听name的值改变了。
    val uppercaseProcessedName = remember {
        derivedStateOf {
            name.value.uppercase()
        }
    }

    // 写法 2
    // 似乎这样做也可以, 但是这样的话 derivedStateOf 就失去了意义
    // 首先，remember有参数，意思是这个参数变了的话，再重组过程中remember括号里的部分才会再次执行
//            val uppercaseProcessedName = remember(name.value) {
//                name.value.uppercase()
//            }

    // 写法1，2真的没区别吗？不是的，我们来换个例子
    Text(
        modifier = Modifier
            .clickable {
                name.value = "Coding with cat iOS"
            },
        text = uppercaseProcessedName.value
    )

}