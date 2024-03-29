package com.swein.shjetpackcompose.importantexamples.recomposetest

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

class ClassRecomposeTestActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val testString = mutableStateOf("Coding with")
        var testUser = User("cat")

        setContent {

            // 例子1: scope 1, 2 都会被重组，有性能风险
            ILog.debug("???", "scope 1")

            Column {

                ILog.debug("???", "scope 2")
                Heavy(testUser)
                Text(
                    modifier = Modifier
                        .clickable {
                            testString.value = "Coding with cat"
                            testUser = User("cat") // 如果这里name值不变，name是val的话，就会跳过。name值改变的话会被重组
                        },
                    text = testString.value
                )

            }
            // 例子1: scope 1, 2 都会被重组，有性能风险

        }
    }
}

data class User(var name: String) {
    // val 的话，变量被赋与新的对象时，如果name值没变就会被跳过，因为compose使用==来进行结构性相等判断
    // var 的话，值没有变也要被重组，就算不赋予新的对象也要被重组，因为compose认为这个类不可靠
}

@Composable
private fun Heavy(user: User) {
    ILog.debug("???", "scope Heavy")
    Text(text = "Heavy ${user.name}")
}