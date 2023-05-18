package com.swein.shjetpackcompose.importantexamples.modifiertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.OnRemeasuredModifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.R

class OnRemeasuredModifierActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // OnRemeasuredModifier 用于定制自己的测量逻辑，或者在测量完成后获得一个回掉来进行业务上的通知处理

            Column {

                Text(
                    text = "Coding with cat",
                    modifier = Modifier
                        .onSizeChanged {
                            // 来自 OnRemeasuredModifier
                            ILog.debug("onSizeChanged", "${it.width} ${it.height}")
                        }
                        .then(object : OnRemeasuredModifier {
                            // 这样写也行，效果和上面是一样的。但是最好用上面的。
                            override fun onRemeasured(size: IntSize) {
                                ILog.debug("onRemeasured", "${size.width} ${size.height}")
                            }
                        })
                )

                Box(
                   modifier = Modifier
                       .padding(20.dp)
                       .then(object : OnRemeasuredModifier {
                           override fun onRemeasured(size: IntSize) {
                               ILog.debug("onRemeasured", "${size.width} ${size.height}")
                               // onRemeasured 会在什么时候被调用？以及这个 size 是指哪个padding的尺寸？？
                               // 还是符合靠右原则，在 padding 40 后，这个回掉会被调用。
                               // 尺寸也是 padding 40 后的尺寸，和 padding 20 无关
                           }
                       })
                       .padding(40.dp)
                )

            }

        }
    }
}