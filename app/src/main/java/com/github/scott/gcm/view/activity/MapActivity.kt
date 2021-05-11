package com.github.scott.gcm.view.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.github.scott.gcm.BuildConfig
import com.github.scott.gcm.R
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.util.*

class MapActivity : AppCompatActivity() {
    private lateinit var googleMap: GoogleMap
    private val TAG = "MAP ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // 지도 초기화
        val supportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync { map -> googleMap = map }


        // 장소 검색
        Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY, Locale.US)
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment?
        autocompleteFragment!!.setPlaceFields(
            listOf(
                Place.Field.LAT_LNG,
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS
            )
        )
        autocompleteFragment.setActivityMode(AutocompleteActivityMode.OVERLAY)
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                if (place.latLng == null) {
                    Log.e(TAG, "LatLng is null")
                } else {
                    addMarker(
                        place.latLng!!,
                        place.name ?: "title is null ",
                        place.address ?: "address is null"
                    )
                }
                Log.i(TAG, "Place: " + place.name + ", " + place.id)

            }

            override fun onError(status: Status) {
                Log.i(TAG, "An error occurred: $status")
            }
        })
    }

    private fun addMarker(latlng: LatLng, title: String, address: String) {
        val markerOptions = MarkerOptions()
        markerOptions.position(latlng)
        markerOptions.title(title)
        markerOptions.snippet(address)
        googleMap.addMarker(markerOptions)
        googleMap.setOnMarkerClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Do you wanna select this place?")
                .setPositiveButton("Yes") { dialog, id ->
                    // FIRE ZE MISSILES!
                    intent.putExtra("title", title)
                    intent.putExtra("address", address)
                    intent.putExtra("lat", latlng.latitude)
                    intent.putExtra("lng", latlng.longitude)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
                .setNegativeButton("No") { dialog, id ->
                    setResult(Activity.RESULT_CANCELED)
                }
            // Create the AlertDialog object and return it
            builder.create()
            builder.show()
            return@setOnMarkerClickListener true
        }
//        zoom
//        1: 세계
//        5: 대륙
//        10: 도시
//        15: 거리
//        20: 건물
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15f))
    }
}