package com.example.nav

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
open class GPSLOC(mainActivity: MainActivity) : LocationListener {
    private val mainthis: MainActivity = mainActivity
    lateinit var locationManager: LocationManager

    private lateinit var tvGpsLocation: TextView
    private val locationPermissionCode = 2

    open fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                mainthis,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mainthis,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                mainthis,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                locationPermissionCode
            )
            false
        } else {
            true
        }
    }

    override fun onProviderDisabled(provider: String) {
        super.onProviderDisabled(provider)
        tvGpsLocation = mainthis.findViewById(R.id.textViewGps)
        tvGpsLocation.text = "NonActiveGPS"
    }
    override fun onProviderEnabled(provider: String) {
        super.onProviderEnabled(provider)
        tvGpsLocation = mainthis.findViewById(R.id.textViewGps)
        tvGpsLocation.text = "ActiveGPS"
    }
    open fun getLocation(){
        locationManager = mainthis.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(mainthis,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(mainthis, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 1f, this)
    }

    override fun  onLocationChanged(location: Location){
        tvGpsLocation = mainthis.findViewById(R.id.textViewGps)


        tvGpsLocation.text = ""+ location.latitude + ","+ location.longitude

    }

}