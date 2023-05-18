package com.swein.shjetpackcompose.importantexamples.modifiertest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.layout
import androidx.compose.ui.modifier.ModifierLocalConsumer
import androidx.compose.ui.modifier.ModifierLocalProvider
import androidx.compose.ui.modifier.ModifierLocalReadScope
import androidx.compose.ui.modifier.ProvidableModifierLocal
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.unit.Constraints
import com.swein.framework.utility.debug.ILog

class ModifierLocalActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // 3. 这里提供一个ModifierLocal，本质是存储一个kv对，好让上下游以相同的kv来共享变量
            val shareKey = modifierLocalOf { "key" }
            Modifier.modifierLocalProvider(shareKey) {
                // 生产者 提供值
                "empty" // 默认值
            }.modifierLocalConsumer {
                // 消费者 取得值
                shareKey.current
            }
            // 到此为止，那么怎么在实际中使用呢？请接着看。


            // 4. 现在来看看实际中怎么用，这种用法很稀少
            val shareWidthKey = modifierLocalOf { "width" }
            // 4. 改一个写法
            Modifier
                .then(object : LayoutModifier, ModifierLocalProvider<String> {
                    // 4. 实现提供者接口（也可以同时实现生产正和消费者）
                    // 4. 提出来
                    private var widthString = ""

                    override fun MeasureScope.measure(
                        measurable: Measurable,
                        constraints: Constraints
                    ): MeasureResult {

                        val placeable = measurable.measure(constraints)

                        widthString = placeable.width.toString()

                        return layout(placeable.width, placeable.height) {
                            placeable.placeRelative(0, 0)
                        }
                    }

                    override val key: ProvidableModifierLocal<String>
                        get() = shareWidthKey // 4. key
                    override val value: String
                        get() = widthString // 4. value
                })
                .then(object : LayoutModifier, ModifierLocalConsumer {
                    // 4. 实现消费者接口（也可以同时实现生产正和消费者）
                    override fun MeasureScope.measure(
                        measurable: Measurable,
                        constraints: Constraints
                    ): MeasureResult {

                        val placeable = measurable.measure(constraints)
                        return layout(placeable.width, placeable.height) {
                            placeable.placeRelative(0, 0)
                        }
                    }

                    override fun onModifierLocalsUpdated(scope: ModifierLocalReadScope) {
                        // 4 取得值，这样就实现了在Modifier之间(上下游)共享数据
                        with(scope) {
                            val widthString = shareWidthKey.current
                            ILog.debug("???", widthString)
                        }
                    }
                })


            // ModifierLocal 用于穿透。多个Modifier之间无法共享数据，所以需要穿透来实现共享。
            // 假设有这么个例子
//            Modifier
//                .layout { measurable, constraints ->
//                    val placeable = measurable.measure(constraints)
//
//                    // 1.加一个变量
//                    val widthString = placeable.width.toString()
//
//                    layout(placeable.width, placeable.height) {
//                        placeable.placeRelative(0, 0)
//                    }
//                }
//                .layout { measurable, constraints ->
//
//                    // 2.这里无法获取widthString这个变量，因为是上游的局部变量。怎么才能获取？
//
//                    val placeable = measurable.measure(constraints)
//                    layout(placeable.width, placeable.height) {
//                        placeable.placeRelative(0, 0)
//                    }
//                }


        }
    }
}