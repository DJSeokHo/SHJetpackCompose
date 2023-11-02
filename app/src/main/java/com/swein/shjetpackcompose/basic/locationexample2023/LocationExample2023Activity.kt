package com.swein.shjetpackcompose.basic.locationexample2023

import android.Manifest
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel

class LocationViewModel: ViewModel() {
    val latitude = mutableDoubleStateOf(0.0)
    val longitude = mutableDoubleStateOf(0.0)
}

class LocationExample2023Activity : AppCompatActivity() {

    private val viewModel: LocationViewModel by viewModels()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                LocationManager.Builder
                    .create(this@LocationExample2023Activity)
                    .request(onUpdateLocation = { latitude, doubleValue ->
                        // one time
                        LocationManager.stop(this@LocationExample2023Activity)

                        viewModel.latitude.doubleValue = latitude
                        viewModel.longitude.doubleValue = doubleValue
                    })

            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            }
            else -> {
                // No location access granted.
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Surface(
                color = Color.White
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = String.format("%6f", viewModel.latitude.doubleValue),
                            color = Color.Black,
                            fontSize = 16.sp
                        )

                        Text(
                            text = String.format("%6f", viewModel.longitude.doubleValue),
                            color = Color.Black,
                            fontSize = 16.sp
                        )

                        Spacer(
                            modifier = Modifier.padding(vertical = 5.dp)
                        )

                        Button(
                            onClick = {

                                locationPermissionRequest.launch(arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION))
                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.Black,
                                backgroundColor = Color.Yellow
                            )
                        ) {

                            Text(
                                text = "Get my location",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
