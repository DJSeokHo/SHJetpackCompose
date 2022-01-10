package com.swein.shjetpackcompose.basic.customthemeexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.swein.shjetpackcompose.basic.customthemeexample.ui.theme.CustomJetpackComposeTheme
import com.swein.shjetpackcompose.basic.customthemeexample.ui.theme.CustomTheme
import com.swein.shjetpackcompose.basic.customthemeexample.ui.theme.CustomThemeManager

class CustomThemeExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            CustomJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = CustomThemeManager.colors.customBackgroundColor) {

                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier.align(Alignment.Center)
                        ) {

                            Button(
                                onClick = {
                                    CustomThemeManager.customTheme = if (CustomThemeManager.customTheme == CustomTheme.DARK) {
                                        CustomTheme.LIGHT
                                    }
                                    else {
                                        CustomTheme.DARK
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = CustomThemeManager.colors.customButtonBackgroundColor,
                                    contentColor = CustomThemeManager.colors.customButtonTextColor
                                )
                            ) {
                                Text(
                                    text = "Change Theme"
                                )
                            }

                            Spacer(modifier = Modifier.padding(vertical = 10.dp))

                            Text(
                                text = "Current theme is ${
                                    if (CustomThemeManager.isSystemInDarkTheme()) {
                                        "Dark"
                                    }
                                    else {
                                        "Light"
                                    }
                                }",
                                color = CustomThemeManager.colors.customTextColor
                            )

                        }
                    }

                }
            }
        }
    }
}
