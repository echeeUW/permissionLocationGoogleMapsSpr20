package com.ericchee.fbisecretlocationtracker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var myLocationManager: MyLocationManager

    companion object {
        private const val REQUEST_LOCATION_PERMISSION_CODE = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        myLocationManager = (application as MyApp).fbiLocationManager

        btnShowMap.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        btnStart.setOnClickListener {

            if (myLocationManager.hasLocationPermission()) {
                // Does have location permission
//                myLocationManager.getLastKnownLocation { location ->
//                    val lat = location.latitude
//                    val long = location.longitude
//
//                    tvCurrentLocation.text = "$lat, $long"
//
//                }

                myLocationManager.startRequrestLocationUpdates()

            } else {
                // Does not have location permission

                AlertDialog.Builder(this)
                        .setTitle("FBI Secret App needs Location Permissions")
                        .setMessage("In order to show you your location on a map, this app needs location permissions")
                        .setPositiveButton("Got it") { _, _ ->
                            ActivityCompat.requestPermissions(
                                    this,
                                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                                    REQUEST_LOCATION_PERMISSION_CODE
                            )
                        }
                        .setNegativeButton("No thanks") { _, _ ->
                            Toast.makeText(this, "Nope clicked", Toast.LENGTH_SHORT).show()
                        }
                        .create()
                        .show()


            }
        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION_CODE -> {
                if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "We have location permission WOOOOOO", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "gg no-re location", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}
