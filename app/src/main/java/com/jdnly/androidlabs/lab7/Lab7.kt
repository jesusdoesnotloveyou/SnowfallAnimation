package com.jdnly.androidlabs.lab7

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import com.jdnly.androidlabs.R

class Lab7 : AppCompatActivity() {

    private val MY_PERMISSIONS_REQUEST_LOCATION = 1
    private var locationManager: LocationManager? = null

    private var currentLatitude : Double = 0.0
    private var currentLongitude : Double = 0.0

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) { showInfo(location) }
        override fun onProviderDisabled(provider: String) { showInfo() }
        override fun onProviderEnabled(provider: String) { showInfo() }
        override fun onStatusChanged(provider: String, status: Int,
                                     extras: Bundle) { showInfo() }
    }

    private fun showInfo(location: Location? = null) {
        val isGpsOn = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkOn = locationManager!!.
        isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        findViewById<TextView>(R.id.gps_status).text =
            if (isGpsOn) "GPS ON" else "GPS OFF"
        findViewById<TextView>(R.id.network_status).text =
            if (isNetworkOn) "Network ON" else "Network OFF"
        if (location != null) {
            currentLongitude = location.longitude
            currentLatitude = location.latitude
            if (location.provider == LocationManager.GPS_PROVIDER) {
                findViewById<TextView>(R.id.gps_coords).text =
                    "GPS: latitude = " + location.latitude.toString() +
                            ", longitude = " + location.longitude.toString()
            }
            if (location.provider == LocationManager.NETWORK_PROVIDER) {
                findViewById<TextView>(R.id.network_coords).text =
                    "Network: latitude = " + location.latitude.toString() +
                            ", longitude = " + location.longitude.toString()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab7)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
    }

    fun startTracking() {
        // Проверяем есть ли разение
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            // Разрешения нет. Нужно ли показать пользователю пояснения?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Показываем пояснения
            } else {
                // Пояснений не требуется, запрашиваем разрешение
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION)
            }
        }
        else {
            // Разрешение есть, выполняем требуемое действие
            locationManager!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 10f, locationListener)
            locationManager!!.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000, 10f, locationListener)
            showInfo()
        }
    }

    fun stopTracking() {
        locationManager!!.removeUpdates(locationListener)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
        // Разрешение есть, заново выполняем требуемое действие
        }
        else { /* Разрешения нет...*/ }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onResume() {
        super.onResume()
        startTracking()
    }
    override fun onPause() {
        super.onPause()
        stopTracking()
    }

    fun buttonOpenSettings(view: View) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:$packageName"))
        startActivity(intent)
    }

    fun buttonAddCoordinates(view: View) {
        // широта
        val editLatitude: EditText = findViewById<EditText>(R.id.latitude)
        // долгота
        val editLongitude: EditText = findViewById<EditText>(R.id.longitude)
        if (editLatitude.text.isEmpty() || editLongitude.text.isEmpty()) {
            val Toast = Toast.makeText(this,
                "You have to fill both fields", Toast.LENGTH_SHORT)
            Toast.show()
        } else {
            val coodinates = Coordinates(editLatitude.text.toString().toDouble(),
                editLongitude.text.toString().toDouble())
            val resultDistance: Double = coodinates.calculateDistance(currentLatitude, currentLongitude)
            findViewById<TextView>(R.id.distance).text = resultDistance.toString()

            findViewById<TextView>(R.id.distanceStatus).text =
                if (coodinates.isPointNear(resultDistance)) {
                "You're within a radius of " +
                        coodinates.getRangeDistance().toString() +
                        "m from the point" }
                else { "Point is too far from you" }
        }
    }
}