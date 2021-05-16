package ie.wit.pcpartsireland.activities.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import ie.wit.pcpartsireland.R
import ie.wit.pcpartsireland.helpers.getAllParts
import ie.wit.pcpartsireland.helpers.setMapMarker
import ie.wit.pcpartsireland.helpers.trackLocation
import ie.wit.pcpartsireland.main.MainApp

class MapsFragment : SupportMapFragment(), OnMapReadyCallback{

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        app.mMap = googleMap
        app.mMap.isMyLocationEnabled = true
        app.mMap.uiSettings.isZoomControlsEnabled = true
        app.mMap.uiSettings.setAllGesturesEnabled(true)
        app.mMap.clear()
        trackLocation(app)
        setMapMarker(app)
        getAllParts(app)
    }
}