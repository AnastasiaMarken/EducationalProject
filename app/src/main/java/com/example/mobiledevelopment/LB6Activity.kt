package com.example.mobiledevelopment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class LB6Activity : AppCompatActivity() {
	private val MY_PERMISSIONS_REQUEST_LOCATION = 1
	private var locationManager: LocationManager? = null

	private lateinit var btn_open_settings: Button
	private lateinit var tv_gps_status: TextView
	private lateinit var tv_gps_coords: TextView
	private lateinit var tv_network_status: TextView
	private lateinit var tv_network_coords: TextView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_lb6)

		btn_open_settings = findViewById(R.id.lb6_btn_open_settings)
		tv_gps_status = findViewById(R.id.lb6_gps_status)
		tv_gps_coords = findViewById(R.id.lb6_gps_coords)
		tv_network_status = findViewById(R.id.lb6_network_status)
		tv_network_coords = findViewById(R.id.lb6_network_coords)

		locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

		btn_open_settings.setOnClickListener {
			val intent = Intent(
				Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
				Uri.parse("package:$packageName"))
			startActivity(intent)
		}
	}

	private val locationListener: LocationListener = object : LocationListener {
		override fun onLocationChanged(location: Location) { showInfo(location) }
		override fun onProviderDisabled(provider: String) { showInfo() }
		override fun onProviderEnabled(provider: String) { showInfo() }
		override fun onStatusChanged(provider: String, status: Int, extras: Bundle) { showInfo() }
	}

	private fun startTracking() {
		if (ContextCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
			} else {
				ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
			}
		} else {
			locationManager!!.requestLocationUpdates(
				LocationManager.GPS_PROVIDER, 1000, 10f, locationListener)
			locationManager!!.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 1000, 0f, locationListener)
			showInfo()
		}
	}

	private fun stopTracking() {
		locationManager!!.removeUpdates(locationListener)
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
											grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
			startTracking()
		}
	}

	override fun onResume() {
		super.onResume()
		startTracking()
	}
	override fun onPause() {
		super.onPause()
		stopTracking()
	}

	private fun showInfo(location: Location? = null) {
		val isGpsOn = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
		val isNetworkOn = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
		tv_gps_status.text = if (isGpsOn) "GPS ON" else "GPS OFF"
		tv_network_status.text = if (isNetworkOn) "Network ON" else "Network OFF"
		if (location != null) {
			if (location.provider == LocationManager.GPS_PROVIDER) {
				tv_gps_coords.text =
					"GPS:\nширота = " + location.latitude.toString() +
							",\nдолгота = " + location.longitude.toString()
				tv_gps_coords.visibility = View.VISIBLE
				System.out.println("change GPS")
			}
			if (location.provider == LocationManager.NETWORK_PROVIDER) {
				tv_network_coords.text =
					"Network:\nширота = " + location.latitude.toString() +
							",\nдолгота = " + location.longitude.toString()
				tv_network_coords.visibility = View.VISIBLE
				System.out.println("change")
			}
		}
	}
}