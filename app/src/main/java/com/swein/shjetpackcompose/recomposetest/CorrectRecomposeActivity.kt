package com.swein.shjetpackcompose.recomposetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.swein.framework.utility.debug.ILog

class CorrectRecomposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val testString = mutableStateOf("Coding with")
        val testUser = mutableStateOf(UserTest("cat"))

        setContent {

            // 例子1: scope 1, 2 都会被重组，有性能风险
            ILog.debug("???", "scope 1")

            Column {

                ILog.debug("???", "scope 2")
                Heavy(testUser.value)
                Text(
                    modifier = Modifier
                        .clickable {
                            testString.value = "Coding with cat"
//                            testUser.value = UserTest("cat1") // 如果这里name值不变，就会跳过。name值改变的话会被重组
                        },
                    text = testString.value
                )

            }
            // 例子1: scope 1, 2 都会被重组，有性能风险

        }
    }
}

// 这是正确的
data class UserTest(val name: String)

// 这也是正确的，但是我本人不太喜欢
//class UserTest(name: String) {
//    val name = mutableStateOf(name)
//}

//@Stable // 加 Stable 意思是让compose 跳过重组这里，程序员给出保证这里是可靠的，不变的。但是不推荐
//data class UserTest(var name: String) {
//    // val 的话，变量被赋与新的对象时，如果name值没变就会被跳过，因为compose使用==来进行结构性相等判断
//    // var 的话，值没有变也要被重组，就算不赋予新的对象也要被重组，因为compose认为这个类不可靠
//}

@Composable
private fun Heavy(user: UserTest) {
    ILog.debug("???", "scope Heavy")
    Text(text = "Heavy ${user.name}")
}