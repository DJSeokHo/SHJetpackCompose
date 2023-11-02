package com.swein.shjetpackcompose.basic.locationexample2023

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Looper
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import com.google.android.gms.location.*
import java.lang.ref.WeakReference

/**
 * need
 * // gps
 * implementation("com.google.android.gms:play-services-location:21.0.1")
 */
object LocationManager {

    private lateinit var activity: WeakReference<ComponentActivity>
    private lateinit var locationRequest: LocationRequest

    private var onUpdateLocation: WeakReference<(latitude: Double, longitude: Double) -> Unit>? = null

    private val locationCallBack = object: LocationCallback() {
        override fun onLocationAvailability(locationAvailability: LocationAvailability) {
            super.onLocationAvailability(locationAvailability)

            if (!locationAvailability.isLocationAvailable) {

                activity.get()?.let {
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    it.startActivity(intent)

                    stop(it)
                }
            }
        }

        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            onUpdateLocation?.get()?.invoke(locationResult.lastLocation?.latitude ?: 0.0, locationResult.lastLocation?.longitude ?: 0.0)
        }
    }

    object Builder {

        fun build(): Builder {
            return this
        }

        fun create(activity: ComponentActivity): LocationManager {
            LocationManager.activity = WeakReference(activity)
            locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).apply {
                setMinUpdateIntervalMillis(1000)
                setMaxUpdateAgeMillis(0)
                setMinUpdateDistanceMeters(1.0f)
                setGranularity(Granularity.GRANULARITY_FINE)
                setWaitForAccurateLocation(true)
            }.build()
            return LocationManager
        }
    }

    fun request(onUpdateLocation: ((latitude: Double, longitude: Double) -> Unit)? = null
    ) {
        LocationManager.onUpdateLocation = WeakReference(onUpdateLocation)

        activity.get()?.let {

            if (ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }

            LocationServices.getFusedLocationProviderClient(it).requestLocationUpdates(
                locationRequest, locationCallBack, Looper.getMainLooper()
            )
        }
    }
    fun stop(activity: Activity) {
        removeCallback(activity)
    }

    private fun removeCallback(activity: Activity) {
        LocationServices.getFusedLocationProviderClient(activity).flushLocations()
        LocationServices.getFusedLocationProviderClient(activity).removeLocationUpdates(
            locationCallBack
        )
    }

}