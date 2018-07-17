package com.apps.alemjc.gymdemo

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import kotlinx.android.synthetic.main.sliding.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    companion object {
        const val PERMISSION_CHECK = 1
        val SAMPLE_DATA = listOf(LatLng(40.5863845, -74.4491746), LatLng(39.9759849, -74.2517769),
                                            LatLng(40.7913549, -74.3272861))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val autocompleteFragment = fragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as PlaceAutocompleteFragment

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                // Log.i(FragmentActivity.TAG, "Place: " + place.name)
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                //Log.i(FragmentActivity.TAG, "An error occurred: $status")
            }
        })

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val intent = Intent(this, TrainersActivity::class.java)
        //val intent = Intent(this, TrainerProfileActivity::class.java) // For testing trainer profile activity

        privateSessionButton.setOnClickListener {
            startActivity(intent) }
        groupSessionButton.setOnClickListener {
            startActivity(intent) }

    }

    fun arePositionsEquals(marker1:LatLng, marker2: LatLng): Boolean {
        return marker1.longitude == marker2.longitude && marker1.latitude == marker2.latitude
    }

    override fun onBackPressed() {

        if (drawer.isOpened()){
            drawer.close()
            return
        }

        super.onBackPressed()
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

        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL


        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){

            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val yourLocation = MarkerOptions().position(LatLng(location.latitude, location.longitude))
            val gymOne = MarkerOptions().position(SAMPLE_DATA[0])
            val gymTwo = MarkerOptions().position(SAMPLE_DATA[1])
            val gymThree = MarkerOptions().position(SAMPLE_DATA[2])

            val cameraPosition = CameraPosition.Builder().target(yourLocation.position)
                    .zoom(8f)
                    .build()

            mMap.addMarker(gymOne)
            mMap.addMarker(gymTwo)
            mMap.addMarker(gymThree)

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))


            mMap.setOnMarkerClickListener  { marker: Marker? ->

                if(marker != null && (arePositionsEquals(marker.position, gymOne.position) || arePositionsEquals(marker.position, gymTwo.position)
                                || arePositionsEquals(marker.position, gymThree.position))){
                    drawer.open()
                }

                false
            }

        }
        else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_CHECK)
        }



    }




}
