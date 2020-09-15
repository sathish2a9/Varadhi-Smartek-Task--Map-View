package com.sathishkumar.varadhismartektask.activities

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sathishkumar.varadhismartektask.R
import java.io.IOException


class MapActivity() : FragmentActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private var address: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragment)
        address = intent.getStringExtra("address")
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                //buildGoogleApiClient()
                mMap!!.isMyLocationEnabled = true
                searchLocation()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                )
            }
        } else {
            //buildGoogleApiClient()
            mMap!!.isMyLocationEnabled = true
            searchLocation()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED)
                    ) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                        searchLocation()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }


    private fun searchLocation() {
        var addressList: List<Address>? = null

        if (address != null || address != "") {
            val geoCoder = Geocoder(this)
            try {
                addressList = geoCoder.getFromLocationName(address, 1)

            } catch (e: IOException) {
                e.printStackTrace()
            }
            val address = addressList!![0]
            val latLng = LatLng(address.latitude, address.longitude)
            mMap!!.addMarker(MarkerOptions().position(latLng).title(this.address))
            mMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            val cameraPosition = CameraPosition.Builder().target(latLng).zoom(14.0f).build()
            val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
            mMap!!.moveCamera(cameraUpdate)

        } else {
            Toast.makeText(applicationContext, "location unavailable", Toast.LENGTH_SHORT).show()
        }
    }

}