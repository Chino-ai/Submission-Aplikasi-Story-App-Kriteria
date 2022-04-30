package com.example.submissionaplikasistoryappkriteria.ui.maps

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.submissionaplikasistoryappkriteria.R
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.GetAllStoriesMapsResponse
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.ListStoryMapsItem
import com.example.submissionaplikasistoryappkriteria.databinding.ActivityMapsBinding
import com.example.submissionaplikasistoryappkriteria.ui.storyuser.ListUserStoryAdapter
import com.example.submissionaplikasistoryappkriteria.ui.storyuser.StoryViewModel
import com.example.submissionaplikasistoryappkriteria.viewModel.ViewModelFactory

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.properties.Delegates

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var mapsViewModel: MapsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapsViewModel = ViewModelProvider(this, ViewModelFactory(this))[MapsViewModel::class.java]

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        mapsViewModel.userStories.observe(this) {
                     it.let {
                         for (review in it) {

                             val userMapStory = LatLng(review.lat, review.lon)
                             mMap.addMarker(
                                 MarkerOptions()
                                     .position(userMapStory)
                                     .title(review.name)
                                     .snippet(review.description)
                             )
                             mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userMapStory, 15f))
                         }

                     }
        }














        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        setMapStyle()
    }

    companion object {
        private const val TAG = "MapsActivity"
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }
}




