package com.swein.shjetpackcompose.examples.previewexample

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Pixel XL", group = "Devices", device = Devices.PIXEL_XL, showSystemUi = true, showBackground = true, fontScale = 1f)
@Preview(name = "Pixel 2", group = "Devices", device = Devices.PIXEL_2, showSystemUi = true, showBackground = true, fontScale = 1f)
@Preview(name = "Pixel 3", group = "Devices", device = Devices.PIXEL_3, showSystemUi = true, showBackground = true, fontScale = 1f)
@Preview(name = "Pixel 4", group = "Devices", device = Devices.PIXEL_4, showSystemUi = true, showBackground = true, fontScale = 1f)
annotation class DevicesPreviews

@Preview(showBackground = true, fontScale = 1f)
@Preview(showBackground = false, fontScale = 1f)
annotation class CustomPreviews