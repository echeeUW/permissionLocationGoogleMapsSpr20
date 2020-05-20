package com.ericchee.fbisecretlocationtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fbiLocationManager: MyLocationManager
    private var marker: Marker? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fbiLocationManager = (application as MyApp).fbiLocationManager
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        fbiLocationManager.startRequrestLocationUpdates()
        fbiLocationManager.onLocationUpdateListener = { location ->


            // Add a marker in Sydney and move the camera
            val newLocation = LatLng(location.latitude, location.longitude)

            this.marker?.let {
                it.position = newLocation
            } ?: run {
                this.marker = mMap.addMarker(MarkerOptions().position(newLocation).title("Marker in Sydney"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 11f))
            }

        }

    }
}
