package com.ericchee.fbisecretlocationtracker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class MyLocationManager(private val context: Context){

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    var onLocationUpdateListener: ((Location) -> Unit)? = null

    fun hasLocationPermission() = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    fun getLastKnownLocation(onLastLocation: (Location) -> Unit) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            val lat = location?.latitude
            val long = location?.longitude

            Log.i("echee", "$lat $long")

            if (location != null) {
                onLastLocation(location)
            }
        }
    }

    fun startRequrestLocationUpdates() {
        val locationRequest = createLocationRequest()

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(context)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            Log.i("echee", "got all the right settings")
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback ,Looper.getMainLooper())

        }
    }



    private fun createLocationRequest(): LocationRequest {
        val locationRequest = LocationRequest.create().apply {
            interval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        return locationRequest
    }

    private val locationCallback: LocationCallback = object: LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult?) {
            Log.i("echee", "Got locatin!!!")

            locationResult?.locations?.forEach { location ->
                val lat = location.latitude
                val long = location.longitude
                Log.i("echee", "$lat, $long")


                onLocationUpdateListener?.invoke(location)

            }
        }

    }




}